package kitchenpos.tobe.eatinorders.application.eatin;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.interfaces.event.EventPublisher;
import kitchenpos.eatinorders.tobe.domain.model.TableCleanedEvent;

public class FakeEventPublisher implements EventPublisher {

    private TableCleanedEvent tableCleanedEvent;

    @Override
    public void publishTableCleaningEvent(TableCleanedEvent event) {
        tableCleanedEvent = event;
    }

    public UUID getCleanedTableId() {
        return tableCleanedEvent.getOrderTableId();
    }
}
