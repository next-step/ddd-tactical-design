package kitchenpos.eatinorders.feedback.domain;

import org.springframework.context.ApplicationEvent;

public class OrderCompletedEvent extends ApplicationEvent {
    private final Long orderId;
    private final Long orderTableId;

    public OrderCompletedEvent(Object source, Long orderId, Long orderTableId) {
        super(source);
        this.orderId = orderId;
        this.orderTableId = orderTableId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getOrderTableId() {
        return orderTableId;
    }
}
