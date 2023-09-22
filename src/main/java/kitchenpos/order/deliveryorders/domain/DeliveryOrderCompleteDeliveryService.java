package kitchenpos.order.deliveryorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.event.OrderStatusChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DeliveryOrderCompleteDeliveryService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher publisher;

    public DeliveryOrderCompleteDeliveryService(OrderRepository orderRepository, ApplicationEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.publisher = publisher;
    }

    public Order completeDelivery(Order order) {
        if (order.getStatus() != OrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }
        this.publisher.publishEvent(new OrderStatusChangeEvent(order.getId(), OrderStatus.DELIVERED));
        return orderRepository.save(order);
    }
}
