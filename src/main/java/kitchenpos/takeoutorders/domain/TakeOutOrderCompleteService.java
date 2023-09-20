package kitchenpos.takeoutorders.domain;

import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class TakeOutOrderCompleteService {

    private final OrderRepository orderRepository;

    public TakeOutOrderCompleteService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order complete(Order order) {
        final OrderStatus status = order.getStatus();
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        order.setStatus(OrderStatus.COMPLETED);
        return orderRepository.save(order);
    }
}
