package kitchenpos.order.tobe.eatinorder.event;

import java.util.UUID;

public class EatInOrderCompleteEvent {

    private final UUID orderTableId;

    public EatInOrderCompleteEvent(UUID orderTableId) {
        this.orderTableId = orderTableId;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
