package kitchenpos.order.supports;

import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.event.OrderStatusChangeEvent;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusChange implements OrderStatusChangeSupport {
    private final OrderRepository orderRepository;

    public OrderStatusChange(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void changeStatus(OrderStatusChangeEvent orderStatusChangeEvent) {
        orderRepository.findById(orderStatusChangeEvent.getOrderId())
                .ifPresent(order -> {
                    order.chageStatus(orderStatusChangeEvent.getOrderStatus());
                    orderRepository.save(order);
                });
    }
}
