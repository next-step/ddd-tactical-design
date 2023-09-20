package kitchenpos.order.deliveryorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
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
