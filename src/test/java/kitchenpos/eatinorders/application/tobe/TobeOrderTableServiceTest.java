package kitchenpos.eatinorders.application.tobe;

import kitchenpos.eatinorders.application.InMemoryOrderRepository;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.application.TobeOrderTableService;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;
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
        final OrderTableForm expected = createOrderTableRequest("1번");
        final TobeOrderTable actual = orderTableService.create(expected);
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
        final OrderTableForm expected = createOrderTableRequest("1번");
        final UUID orderTableId = orderTableService.create(expected).getId();
        final TobeOrderTable actual = orderTableService.sit(orderTableId);
        assertThat(actual.isEmpty()).isFalse();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final OrderTableForm expected = createOrderTableRequest("1번");
        final UUID orderTableId = orderTableService.create(expected).getId();
        final TobeOrderTable actual = orderTableService.clear(orderTableId);
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

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final TobeOrderTable expected = changeNumberOfGuestsRequest(4);
        final TobeOrderTable actual = orderTableService.changeNumberOfGuests(orderTableId, expected);
        assertThat(actual.getNumberOfGuests()).isEqualTo(4);
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final TobeOrderTable expected = changeNumberOfGuestsRequest(numberOfGuests);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
        final TobeOrderTable expected = changeNumberOfGuestsRequest(4);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderTableRepository.save(orderTable());
        final List<OrderTable> actual = orderTableService.findAll();
        assertThat(actual).hasSize(1);
    }
*/
    private OrderTableForm createOrderTableRequest(final String name) {
        final OrderTableForm orderTable = new OrderTableForm();
        orderTable.setName(name);
        return orderTable;
    }
}
