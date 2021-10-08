package kitchenpos.eatinorders.tobe.domain.interfaces.event;

import kitchenpos.eatinorders.tobe.domain.model.TableCleanedEvent;

public interface OrderTableEventListener {

    void handleOrderTableCleanedEvent(TableCleanedEvent event);
}
