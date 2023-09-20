package kitchenpos.order.deliveryorders.domain;

import kitchenpos.order.deliveryorders.infra.KitchenridersClient;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DeliveryOrderAcceptService {

    private final OrderRepository orderRepository;
    private final KitchenridersClient kitchenridersClient;

    public DeliveryOrderAcceptService(OrderRepository orderRepository, KitchenridersClient kitchenridersClient) {
        this.orderRepository = orderRepository;
        this.kitchenridersClient = kitchenridersClient;
    }

    public Order accept(Order order) {
        if (order.getStatus() != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        BigDecimal sum = order.getOrderLineItems().sum();
        kitchenridersClient.requestDelivery(order.getId(), sum, order.getDeliveryAddress());
        order.setStatus(OrderStatus.ACCEPTED);
        return orderRepository.save(order);
    }
}
