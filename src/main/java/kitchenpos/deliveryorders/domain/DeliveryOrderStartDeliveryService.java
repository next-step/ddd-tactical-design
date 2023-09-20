package kitchenpos.deliveryorders.domain;

import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import org.springframework.stereotype.Component;

@Component
public class DeliveryOrderStartDeliveryService {

    private final OrderRepository orderRepository;

    public DeliveryOrderStartDeliveryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order startDelivery(Order order) {
        if (order.getType() != OrderType.DELIVERY) {
            throw new IllegalStateException();
        }
        if (order.getStatus() != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        order.setStatus(OrderStatus.DELIVERING);
        return orderRepository.save(order);
    }
}
