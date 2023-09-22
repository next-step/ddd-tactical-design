package kitchenpos.order.supports;

import kitchenpos.order.event.OrderStatusChangeEvent;
import org.springframework.context.event.EventListener;

public interface OrderStatusChangeSupport {
    @EventListener
    void changeStatus(final OrderStatusChangeEvent orderStatusChangeEvent);
}
