package kitchenpos.eatinorders.publisher;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class OrderTableClearEvent extends ApplicationEvent {

    private final UUID orderTableId;

    public OrderTableClearEvent(Object source, UUID orderTableId) {
        super(source);
        this.orderTableId = orderTableId;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
