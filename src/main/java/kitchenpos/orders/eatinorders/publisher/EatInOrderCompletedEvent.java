package kitchenpos.orders.eatinorders.publisher;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class EatInOrderCompletedEvent extends ApplicationEvent {

    private final UUID orderTableId;

    public EatInOrderCompletedEvent(Object source, UUID orderTableId) {
        super(source);
        this.orderTableId = orderTableId;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
