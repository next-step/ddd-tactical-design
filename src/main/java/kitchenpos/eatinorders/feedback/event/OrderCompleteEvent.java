package kitchenpos.eatinorders.feedback.event;

import org.springframework.context.ApplicationEvent;

public class OrderCompleteEvent extends ApplicationEvent {
    private final Long orderTableId;

    public OrderCompleteEvent(Object source, Long orderTableId) {
        super(source);
        this.orderTableId = orderTableId;
    }

    public Long getOrderTableId() {
        return orderTableId;
    }
}
