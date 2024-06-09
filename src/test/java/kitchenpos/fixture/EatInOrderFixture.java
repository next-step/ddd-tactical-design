package kitchenpos.fixture;

import kitchenpos.eatinorders.todo.domain.orders.EatInOrder;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderStatus;
import kitchenpos.eatinorders.todo.domain.ordertables.NumberOfGuests;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTableName;

import java.util.List;

import static kitchenpos.fixture.Fixtures.orderLineItem;

public class EatInOrderFixture {
    public static EatInOrder eatInOrder(final EatInOrderStatus status) {
        return eatInOrder(status, OrderTable.from(OrderTableName.from("1번")));
    }

    public static EatInOrder eatInOrder(final EatInOrderStatus status, final OrderTable orderTable) {
        return EatInOrder.of(status, List.of(orderLineItem()), orderTable.getId());
    }

    public static OrderTable orderTable() {
        return orderTable(false, 0);
    }

    public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        return OrderTable.from(OrderTableName.from("1번"), NumberOfGuests.from(numberOfGuests), occupied);
    }
}
