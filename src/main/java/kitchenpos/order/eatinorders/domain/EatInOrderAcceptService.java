package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class EatInOrderAcceptService {

    private final OrderRepository orderRepository;

    public EatInOrderAcceptService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order accept(Order order) {
        if (order.getStatus() != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        order.setStatus(OrderStatus.ACCEPTED);
        return orderRepository.save(order);
    }
}
