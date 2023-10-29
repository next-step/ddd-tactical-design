package kitchenpos.eatinorders.domain;

import java.util.UUID;

public class OrderCompleteEvent {

    private final UUID orderTableId;

    private OrderCompleteEvent(UUID orderTableId) {
        this.orderTableId = orderTableId;
    }

    public static OrderCompleteEvent create(UUID orderTableId) {
        return new OrderCompleteEvent(orderTableId);
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
