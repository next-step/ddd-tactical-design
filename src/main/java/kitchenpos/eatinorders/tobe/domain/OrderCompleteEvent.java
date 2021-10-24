package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

public class OrderCompleteEvent {
    private UUID tableId;

    public OrderCompleteEvent(UUID tableId) {
        this.tableId = tableId;
    }

    public UUID getTableId() {
        return tableId;
    }
}
