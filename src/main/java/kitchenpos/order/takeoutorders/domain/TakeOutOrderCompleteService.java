package kitchenpos.order.takeoutorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
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
        order.chageStatus(OrderStatus.COMPLETED);
        return orderRepository.save(order);
    }
}
