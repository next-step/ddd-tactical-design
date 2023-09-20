package kitchenpos.order.takeoutorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class TakeOutOrderServeService {

    private final OrderRepository orderRepository;

    public TakeOutOrderServeService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order serve(Order order) {
        if (order.getStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        order.setStatus(OrderStatus.SERVED);
        return orderRepository.save(order);
    }
}
