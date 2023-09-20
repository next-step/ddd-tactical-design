package kitchenpos.deliveryorders.domain;

import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
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
        order.setStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }
}
