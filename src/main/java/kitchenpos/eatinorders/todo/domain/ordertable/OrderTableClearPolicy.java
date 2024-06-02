package kitchenpos.eatinorders.todo.domain.ordertable;

import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;

import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.ORDER_TABLE_CONTAINS_INVALID_ORDER;

public class OrderTableClearPolicy {
    private final OrderClient orderClient;

    public OrderTableClearPolicy(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    public void checkClear(OrderTable orderTable) {
        if (orderClient.containsInvalidOrderForClearOrderTable(orderTable)) {
            throw new KitchenPosIllegalStateException(ORDER_TABLE_CONTAINS_INVALID_ORDER, orderTable.getId());
        }
    }
}
