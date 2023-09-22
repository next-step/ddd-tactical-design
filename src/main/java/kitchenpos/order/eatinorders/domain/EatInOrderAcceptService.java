package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.event.OrderStatusChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EatInOrderAcceptService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher publisher;

    public EatInOrderAcceptService(OrderRepository orderRepository, ApplicationEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.publisher = publisher;
    }

    public Order accept(Order order) {
        if (order.getStatus() != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        this.publisher.publishEvent(new OrderStatusChangeEvent(order.getId(), OrderStatus.ACCEPTED));
        return orderRepository.save(order);
    }
}
