package kitchenpos.eatinorders.todo.domain.ordertables;

import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;

import java.util.UUID;

import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.ORDER_TABLE_CONTAINS_INVALID_ORDER;

public class OrderTableClearPolicy {
    private final OrderClient orderClient;

    public OrderTableClearPolicy(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    public void checkClear(UUID orderTableId) {
        if (orderClient.containsInvalidOrderForClearOrderTable(orderTableId)) {
            throw new KitchenPosIllegalStateException(ORDER_TABLE_CONTAINS_INVALID_ORDER, orderTableId);
        }
    }
}
