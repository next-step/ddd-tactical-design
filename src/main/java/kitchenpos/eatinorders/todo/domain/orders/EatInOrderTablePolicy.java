package kitchenpos.eatinorders.todo.domain.orders;

import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;

import java.util.UUID;

public class EatInOrderTablePolicy {
    private final OrderTableClient orderTableClient;

    public EatInOrderTablePolicy(OrderTableClient orderTableClient) {
        this.orderTableClient = orderTableClient;
    }

    public void checkClearOrderTable(UUID orderTableId) {
        OrderTable orderTable = orderTableClient.getOrderTable(orderTableId);
        orderTable.checkClear();
    }
}
