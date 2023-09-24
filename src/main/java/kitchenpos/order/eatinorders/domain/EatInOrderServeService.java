package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class EatInOrderServeService {

    private final OrderRepository orderRepository;

    public EatInOrderServeService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order serve(Order order) {
        if (order.getStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        order.chageStatus(OrderStatus.SERVED);
        return orderRepository.save(order);
    }
}
