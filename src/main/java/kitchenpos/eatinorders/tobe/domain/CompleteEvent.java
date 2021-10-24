package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

public class CompleteEvent {
    private UUID tableId;

    public CompleteEvent(UUID tableId) {
        this.tableId = tableId;
    }

    public UUID getTableId() {
        return tableId;
    }
}
