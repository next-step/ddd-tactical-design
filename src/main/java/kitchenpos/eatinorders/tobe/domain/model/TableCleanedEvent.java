package kitchenpos.eatinorders.tobe.domain.model;

import java.util.UUID;

public class TableCleanedEvent {

    private UUID orderTableId;

    public TableCleanedEvent(UUID orderTableId) {
        this.orderTableId = orderTableId;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
