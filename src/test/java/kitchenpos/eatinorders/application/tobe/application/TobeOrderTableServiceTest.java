package kitchenpos.eatinorders.application.tobe.application;

import kitchenpos.eatinorders.application.InMemoryOrderRepository;
import kitchenpos.eatinorders.application.tobe.domain.TobeInMemoryOrderTableRepository;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.tobe.application.TobeOrderTableService;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTableRepository;
import kitchenpos.eatinorders.tobe.ui.OrderTableForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TobeOrderTableServiceTest {

    private TobeOrderTableRepository orderTableRepository;
    private OrderRepository orderRepository;
    private TobeOrderTableService orderTableService;

    @BeforeEach
    void setUp() {
        orderTableRepository = new TobeInMemoryOrderTableRepository();
        orderRepository = new InMemoryOrderRepository();
        orderTableService = new TobeOrderTableService(orderTableRepository, orderRepository);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        final OrderTableForm expected = createOrderTableRequest();
        final OrderTableForm actual = orderTableService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getNumberOfGuests()).isZero(),
            () -> assertThat(actual.isEmpty()).isTrue()
        );
    }

    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        final OrderTableForm expected = createOrderTableRequest(name);
        assertThatThrownBy(() -> orderTableService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        final OrderTableForm expected = createOrderTableRequest();
        final UUID orderTableId = orderTableService.create(expected).getId();
        final OrderTableForm actual = orderTableService.sit(orderTableId);
        assertThat(actual.isEmpty()).isFalse();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final OrderTableForm expected = createOrderTableRequest();
        final UUID orderTableId = orderTableService.create(expected).getId();
        final OrderTableForm actual = orderTableService.clear(orderTableId);
        assertAll(
            () -> assertThat(actual.getNumberOfGuests()).isZero(),
            () -> assertThat(actual.isEmpty()).isTrue()
        );
    }

/*
    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(false, 4));
        final UUID orderTableId = orderTable.getId();
        orderRepository.save(order(OrderStatus.ACCEPTED, orderTable));
        assertThatThrownBy(() -> orderTableService.clear(orderTableId))
            .isInstanceOf(IllegalStateException.class);
    }
*/

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final OrderTableForm orderTable = orderTableService.create(createOrderTableRequest());
        final OrderTableForm expected = changeNumberOfGuestsRequest(4);

        orderTableService.sit(orderTable.getId());
        final OrderTableForm actual = orderTableService.changeNumberOfGuests(orderTable.getId(), expected);

        assertThat(actual.getNumberOfGuests()).isEqualTo(4);
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final OrderTableForm orderTable = orderTableService.create(createOrderTableRequest());
        final OrderTableForm expected = changeNumberOfGuestsRequest(numberOfGuests);

        orderTableService.sit(orderTable.getId());

        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTable.getId(), expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final OrderTableForm orderTable = orderTableService.create(createOrderTableRequest());
        final OrderTableForm expected = changeNumberOfGuestsRequest(4);

        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTable.getId(), expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderTableService.create(createOrderTableRequest());
        final List<OrderTableForm> actual = orderTableService.findAll();
        assertThat(actual).hasSize(1);
    }

    private OrderTableForm createOrderTableRequest() {
        return createOrderTableRequest("1번");
    }

    private OrderTableForm createOrderTableRequest(final String name) {
        final OrderTableForm orderTable = new OrderTableForm();
        orderTable.setName(name);
        return orderTable;
    }

    private OrderTableForm changeNumberOfGuestsRequest(final int numberOfGuests) {
        final OrderTableForm orderTable = new OrderTableForm();
        orderTable.setNumberOfGuests(numberOfGuests);
        return orderTable;
    }
}
