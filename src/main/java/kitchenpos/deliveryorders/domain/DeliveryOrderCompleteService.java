package kitchenpos.deliveryorders.domain;

import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class DeliveryOrderCompleteService {

    private final OrderRepository orderRepository;

    public DeliveryOrderCompleteService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order complete(Order order) {
        final OrderStatus status = order.getStatus();

        if (status != OrderStatus.DELIVERED) {
            throw new IllegalStateException();
        }
        order.setStatus(OrderStatus.COMPLETED);
        return orderRepository.save(order);
    }
}
