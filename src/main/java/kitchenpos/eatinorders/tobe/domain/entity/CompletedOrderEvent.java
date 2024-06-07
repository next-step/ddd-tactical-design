package kitchenpos.eatinorders.tobe.domain.entity;

import java.util.UUID;

public class CompletedOrderEvent {
    private UUID orderTableId;

    public CompletedOrderEvent(UUID orderTableId) {
        this.orderTableId = orderTableId;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

}
