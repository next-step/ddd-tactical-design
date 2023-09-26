package kitchenpos.order.takeoutorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class TakeOutOrderAcceptService {

    private final OrderRepository orderRepository;

    public TakeOutOrderAcceptService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order accept(Order order) {
        if (order.getStatus() != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        order.chageStatus(OrderStatus.ACCEPTED);
        return orderRepository.save(order);
    }
}
