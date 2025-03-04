package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.event.Event;
import kitchenpos.eatinorders.tobe.domain.common.OrderId;

public class OrderCompleteEvent extends Event {

    private final OrderId id;

    public OrderCompleteEvent(OrderId id) {
        this.id = id;
    }

    public OrderId getId() {
        return id;
    }
}
