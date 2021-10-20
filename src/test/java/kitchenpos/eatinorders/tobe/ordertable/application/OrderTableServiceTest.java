package kitchenpos.eatinorders.tobe.ordertable.application;

import kitchenpos.eatinorders.tobe.application.EatInOrderTableService;
import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderRepository;
import kitchenpos.eatinorders.tobe.eatinorder.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.eatinorder.infra.InMemoryOrderRepository;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;
import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTableRepository;
import kitchenpos.eatinorders.tobe.ordertable.infra.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.ordertable.ui.dto.ChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.tobe.ordertable.ui.dto.CreateRequest;
import kitchenpos.fixture.EatInOrderFixture;
import kitchenpos.fixture.OrderTableFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.order;
import static kitchenpos.Fixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문테이블 응용서비스(OrderTableService)는")
class OrderTableServiceTest {
    private OrderTableRepository orderTableRepository;
    private OrderRepository orderRepository;
    private OrderTableService orderTableService;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        orderRepository = new InMemoryOrderRepository();
        final EatInOrderTableService eatInOrderTableService = new EatInOrderTableService(orderRepository, orderTableRepository);
        orderTableService = new OrderTableService(eatInOrderTableService);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        final CreateRequest expected = createOrderTableRequest("1번");
        final OrderTable actual = orderTableService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getNumberOfGuests()).isZero(),
                () -> assertThat(actual.isEmpty()).isTrue()
        );
    }

    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        final CreateRequest expected = createOrderTableRequest(name);
        assertThatThrownBy(() -> orderTableService.create(expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        final UUID orderTableId = orderTableRepository.save(OrderTableFixture.주문테이블()).getId();
        final OrderTable actual = orderTableService.sit(orderTableId);
        assertThat(actual.isEmpty()).isFalse();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final UUID orderTableId = orderTableRepository.save(OrderTableFixture.앉은테이블(4)).getId();
        final OrderTable actual = orderTableService.clear(orderTableId);
        assertAll(
                () -> assertThat(actual.getNumberOfGuests()).isZero(),
                () -> assertThat(actual.isEmpty()).isTrue()
        );
    }

    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        final OrderTable orderTable = orderTableRepository.save(OrderTableFixture.앉은테이블(4));
        final UUID orderTableId = orderTable.getId();
        orderRepository.save(EatInOrderFixture.수락된_매장주문(orderTable));
        assertThatThrownBy(() -> orderTableService.clear(orderTableId))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final UUID orderTableId = orderTableRepository.save(OrderTableFixture.앉은테이블()).getId();
        final ChangeNumberOfGuestsRequest expected = changeNumberOfGuestsRequest(4);
        final OrderTable actual = orderTableService.changeNumberOfGuests(orderTableId, expected);
        assertThat(actual.getNumberOfGuests()).isEqualTo(4);
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final UUID orderTableId = orderTableRepository.save(OrderTableFixture.앉은테이블()).getId();
        final ChangeNumberOfGuestsRequest expected = changeNumberOfGuestsRequest(numberOfGuests);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final UUID orderTableId = orderTableRepository.save(OrderTableFixture.주문테이블()).getId();
        final ChangeNumberOfGuestsRequest expected = changeNumberOfGuestsRequest(4);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderTableRepository.save(OrderTableFixture.주문테이블());
        final List<OrderTable> actual = orderTableService.findAll();
        assertThat(actual).hasSize(1);
    }

    private CreateRequest createOrderTableRequest(final String name) {
        return new CreateRequest(name);
    }

    private ChangeNumberOfGuestsRequest changeNumberOfGuestsRequest(final int numberOfGuests) {
        return new ChangeNumberOfGuestsRequest(numberOfGuests);
    }
}
