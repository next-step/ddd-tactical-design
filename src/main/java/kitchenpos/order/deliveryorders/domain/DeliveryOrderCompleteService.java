package kitchenpos.order.deliveryorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.event.OrderStatusChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DeliveryOrderCompleteService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher publisher;

    public DeliveryOrderCompleteService(OrderRepository orderRepository, ApplicationEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.publisher = publisher;
    }

    public Order complete(Order order) {
        final OrderStatus status = order.getStatus();

        if (status != OrderStatus.DELIVERED) {
            throw new IllegalStateException();
        }
        this.publisher.publishEvent(new OrderStatusChangeEvent(order.getId(), OrderStatus.COMPLETED));
        return orderRepository.save(order);
    }
}
