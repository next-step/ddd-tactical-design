package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderTable;
import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderTableRepository;
import kitchenpos.eatinorders.domain.tobe.domain.vo.Guests;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableName;
import kitchenpos.eatinorders.domain.tobe.domain.vo.TableEmptyStatus;
import kitchenpos.eatinorders.dto.*;
import kitchenpos.menus.application.InMemoryTobeMenuRepository;
import kitchenpos.menus.domain.tobe.domain.TobeMenuRepository;
import kitchenpos.support.infra.FakePurgomalumClient;
import kitchenpos.support.infra.profanity.Profanity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static kitchenpos.Fixtures.eatInOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TobeOrderTableServiceTest {
    private TobeMenuRepository menuRepository;
    private TobeOrderTableRepository orderTableRepository;
    private EatInOrderRepository orderRepository;
    private TobeOrderTableService orderTableService;
    private Profanity profanity;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryTobeMenuRepository();
        orderTableRepository = new InMemoryTobeOrderTableRepository();
        orderRepository = new InMemoryEatInOrderRepository();
        profanity = new FakePurgomalumClient();
        orderTableService = new TobeOrderTableService(orderTableRepository, orderRepository, profanity);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        //given
        final OrderTableRegisterRequest request = new OrderTableRegisterRequest("1번");
        //when
        final OrderTableResponse table = orderTableService.create(request);
        //then
        assertThat(table).isNotNull();
        assertAll(
                () -> assertThat(table.getName()).isEqualTo("1번"),
                () -> assertThat(table.isEmpty()).isTrue(),
                () -> assertThat(table.getNumberOfGuests()).isZero()
        );
    }

    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        //given
        OrderTableRegisterRequest request = new OrderTableRegisterRequest(name);
        //when&&then
        assertThatThrownBy(() -> orderTableService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        //given
        final TobeOrderTable table= orderTableRepository.save(
                TobeOrderTable.Of(new OrderTableName("1번", profanity))
        );
        final OrderTableSitRequest request = new OrderTableSitRequest(table.getId().getValue());
        //when
        final OrderTableResponse response = orderTableService.sit(request);
        //then
        assertAll(
                () -> assertThat(response.isEmpty()).isFalse(),
                () -> assertThat(response.getNumberOfGuests()).isZero(),
                () -> assertThat(table.isEmpty()).isFalse(),
                () -> assertThat(table.getNumberOfGuests()).isZero()
        );
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        //given
        final TobeOrderTable table= orderTableRepository.save(
                TobeOrderTable.Of(new OrderTableName("1번", profanity), new Guests(2), TableEmptyStatus.OCCUPIED)
        );
        final OrderTableClearRequest request = new OrderTableClearRequest(table.getId().getValue());
        //when
        final OrderTableResponse response = orderTableService.clear(request);
        //then
        assertAll(
                () -> assertThat(response.isEmpty()).isTrue(),
                () -> assertThat(response.getNumberOfGuests()).isZero(),
                () -> assertThat(table.isEmpty()).isTrue(),
                () -> assertThat(table.getNumberOfGuests()).isZero()
        );
    }

    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        //given
        final TobeOrderTable table = orderTableRepository.save(
                TobeOrderTable.Of(new OrderTableName("1번", profanity), new Guests(2), TableEmptyStatus.OCCUPIED)
        );
        orderRepository.save(eatInOrder(table));
        final OrderTableClearRequest request = new OrderTableClearRequest(table.getId().getValue());
        //when&&then
        assertThatThrownBy(() ->  orderTableService.clear(request))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        //given
        final TobeOrderTable table = orderTableRepository.save(
                TobeOrderTable.Of(new OrderTableName("1번", profanity), new Guests(2), TableEmptyStatus.OCCUPIED)
        );
        final ChangeNumberOfGuestsRequest request = new ChangeNumberOfGuestsRequest(table.getId().getValue(), 1);
        //when
        final OrderTableResponse response = orderTableService.changeNumberOfGuests(request);
        //then
        //then
        assertAll(
                () -> assertThat(response.getNumberOfGuests()).isOne(),
                () -> assertThat(table.getNumberOfGuests()).isOne()
        );
    }


    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        //given
        final TobeOrderTable table = orderTableRepository.save(
                TobeOrderTable.Of(new OrderTableName("1번", profanity), new Guests(2), TableEmptyStatus.OCCUPIED)
        );
        final ChangeNumberOfGuestsRequest request = new ChangeNumberOfGuestsRequest(table.getId().getValue(), 1);
        //when
        final OrderTableResponse response = orderTableService.changeNumberOfGuests(request);
        //then
        //then
        assertAll(
                () -> assertThat(response.getNumberOfGuests()).isOne(),
                () -> assertThat(table.getNumberOfGuests()).isOne()
        );
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderTableRepository.save(TobeOrderTable.Of(new OrderTableName("1번", profanity), new Guests(2), TableEmptyStatus.OCCUPIED));
        final List<OrderTableResponse> actual = orderTableService.findAll();
        assertThat(actual).hasSize(1);
    }
}
