package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.event.OrderStatusChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EatInOrderServeService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher publisher;

    public EatInOrderServeService(OrderRepository orderRepository, ApplicationEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.publisher = publisher;
    }

    public Order serve(Order order) {
        if (order.getStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        this.publisher.publishEvent(new OrderStatusChangeEvent(order.getId(), OrderStatus.SERVED));
        return orderRepository.save(order);
    }
}
