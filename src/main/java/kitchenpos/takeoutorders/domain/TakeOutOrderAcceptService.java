package kitchenpos.takeoutorders.domain;

import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
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
        order.setStatus(OrderStatus.ACCEPTED);
        return orderRepository.save(order);
    }
}
