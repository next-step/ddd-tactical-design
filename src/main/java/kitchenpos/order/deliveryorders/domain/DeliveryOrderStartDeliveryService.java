package kitchenpos.order.deliveryorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.domain.OrderType;
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
        order.chageStatus(OrderStatus.DELIVERING);
        return orderRepository.save(order);
    }
}
