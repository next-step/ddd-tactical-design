package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.*;
import kitchenpos.fixture.OrderFixture;
import kitchenpos.fixture.OrderTableFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.UUID;

import static kitchenpos.fixture.MenuFixture.menu;
import static kitchenpos.fixture.MenuGroupFixture.menuGroup;
import static kitchenpos.fixture.MenuProductFixture.menuProduct;
import static kitchenpos.fixture.OrderFixture.eatInOrder;
import static kitchenpos.fixture.OrderFixture.orderLineItem;
import static kitchenpos.fixture.OrderTableFixture.orderTable;
import static kitchenpos.fixture.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTableServiceTest {
    private OrderTableRepository orderTableRepository;
    private OrderRepository orderRepository;
    private OrderTableService orderTableService;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        orderRepository = new InMemoryOrderRepository();
        orderTableService = new OrderTableService(orderTableRepository, orderRepository);
    }

    @DisplayName("주문 테이블을 등록할 수 있다.")
    @Test
    void create() {

        final OrderTable expected = orderTable();
        final OrderTable actual = orderTableService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getNumberOfGuests()).isZero(),
            () -> assertThat(actual.isOccupied()).isFalse()
        );
    }

    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        final OrderTable expected = orderTable(name, 0, false);
        assertThatThrownBy(() -> orderTableService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블을 해지할 수 있다.")
    @Test
    void sit() {
        final UUID orderTableId = orderTableRepository.save(orderTable(0, false)).getId();
        final OrderTable actual = orderTableService.sit(orderTableId);
        assertThat(actual.isOccupied()).isTrue();
    }

    @DisplayName("빈 테이블로 설정할 수 있다.")
    @Test
    void clear() {
        final UUID orderTableId = orderTableRepository.save(orderTable(4, true)).getId();
        final OrderTable actual = orderTableService.clear(orderTableId);
        assertAll(
            () -> assertThat(actual.getNumberOfGuests()).isZero(),
            () -> assertThat(actual.isOccupied()).isFalse()
        );
    }

    @DisplayName("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.")
    @Test
    void clearWithUncompletedOrders() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        final UUID orderTableId = orderTable.getId();

        final Order order = eatInOrder(orderTable, OrderStatus.SERVED,
                List.of(orderLineItem(menu(menuGroup(), List.of(menuProduct(product())))))
        );
        orderRepository.save(order);
        assertThatThrownBy(() -> orderTableService.clear(orderTableId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Test
    void changeNumberOfGuests() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(0, true));
        final OrderTable expected = orderTable(orderTable.getId(), orderTable.getName(), 4, orderTable.isOccupied());
        final OrderTable actual = orderTableService.changeNumberOfGuests(orderTable.getId(), expected);
        assertThat(actual.getNumberOfGuests()).isEqualTo(4);
    }

    @DisplayName("방문한 손님 수가 올바르지 않으면 변경할 수 없다.")
    @ValueSource(ints = -1)
    @ParameterizedTest
    void changeNumberOfGuests(final int numberOfGuests) {
        final OrderTable orderTable = orderTableRepository.save(orderTable(0, true));
        final OrderTable expected = orderTable(orderTable.getId(), orderTable.getName(), numberOfGuests, orderTable.isOccupied());
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTable.getId(), expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블은 방문한 손님 수를 변경할 수 없다.")
    @Test
    void changeNumberOfGuestsInEmptyTable() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(0, false));
        final OrderTable expected = orderTable(orderTable.getId(), orderTable.getName(), 4, orderTable.isOccupied());
        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTable.getId(), expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        orderTableRepository.save(orderTable());
        final List<OrderTable> actual = orderTableService.findAll();
        assertThat(actual).hasSize(1);
    }
}
