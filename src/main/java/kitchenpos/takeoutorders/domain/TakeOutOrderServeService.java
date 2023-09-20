package kitchenpos.takeoutorders.domain;

import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
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
