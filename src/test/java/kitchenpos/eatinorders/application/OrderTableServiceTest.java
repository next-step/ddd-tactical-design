package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.todo.domain.orders.EatInOrderRepository;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderStatus;
import kitchenpos.eatinorders.dto.OrderTableResponse;
import kitchenpos.eatinorders.todo.domain.ordertables.NumberOfGuests;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTableClearPolicy;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTableName;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.UUID;

import static kitchenpos.fixture.EatInOrderFixture.eatInOrder;
import static kitchenpos.fixture.EatInOrderFixture.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTableServiceTest {
    private OrderTableRepository orderTableRepository;
    private EatInOrderRepository orderRepository;
    private OrderTableService orderTableService;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        orderRepository = new InMemoryEatInOrderRepository();
        OrderTableClearPolicy orderTableClearPolicy = new OrderTableClearPolicy(orderRepository);
        orderTableService = new OrderTableService(orderTableRepository, orderTableClearPolicy);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {
        final OrderTableName expected = OrderTableName.from("1번");
        final OrderTableResponse actual = orderTableService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.id()).isNotNull(),
            () -> assertThat(actual.name()).isEqualTo(expected.nameValue()),
            () -> assertThat(actual.numberOfGuests()).isZero(),
            () -> assertThat(actual.occupied()).isFalse()
        );
    }

    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> OrderTableName.from(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final OrderTableResponse actual = orderTableService.sit(orderTableId);
        assertThat(actual.occupied()).isTrue();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 4)).getId();
        final OrderTableResponse actual = orderTableService.clear(orderTableId);
        assertAll(
            () -> assertThat(actual.numberOfGuests()).isZero(),
            () -> assertThat(actual.occupied()).isFalse()
        );
    }

    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final UUID orderTableId = orderTable.getId();
        orderRepository.save(eatInOrder(EatInOrderStatus.ACCEPTED, orderTable));
        assertThatThrownBy(() -> orderTableService.clear(orderTableId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
        final NumberOfGuests expected = NumberOfGuests.from(4);
        final OrderTableResponse actual = orderTableService.changeNumberOfGuests(orderTableId, expected);
        assertThat(actual.numberOfGuests()).isEqualTo(4);
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final UUID orderTableId = orderTableRepository.save(orderTable(true, 0)).getId();
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, NumberOfGuests.from(numberOfGuests)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final UUID orderTableId = orderTableRepository.save(orderTable(false, 0)).getId();
        final NumberOfGuests expected = NumberOfGuests.from(4);
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTableId, expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderTableRepository.save(orderTable());
        final List<OrderTableResponse> actual = orderTableService.findAll();
        assertThat(actual).hasSize(1);
    }
}
