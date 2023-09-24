package kitchenpos.order.deliveryorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class DeliveryOrderCompleteDeliveryService {

    private final OrderRepository orderRepository;

    public DeliveryOrderCompleteDeliveryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order completeDelivery(Order order) {
        if (order.getStatus() != OrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }
        order.chageStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }
}
