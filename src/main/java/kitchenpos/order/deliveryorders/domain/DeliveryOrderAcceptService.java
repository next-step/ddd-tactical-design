package kitchenpos.order.deliveryorders.domain;

import kitchenpos.order.deliveryorders.infra.KitchenridersClient;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.event.OrderStatusChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DeliveryOrderAcceptService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher publisher;
    private final KitchenridersClient kitchenridersClient;

    public DeliveryOrderAcceptService(OrderRepository orderRepository, ApplicationEventPublisher publisher, KitchenridersClient kitchenridersClient) {
        this.orderRepository = orderRepository;
        this.publisher = publisher;
        this.kitchenridersClient = kitchenridersClient;
    }

    public Order accept(Order order) {
        if (order.getStatus() != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        BigDecimal sum = order.getOrderLineItems().sum();
        this.kitchenridersClient.requestDelivery(order.getId(), sum, order.getDeliveryAddress());
        this.publisher.publishEvent(new OrderStatusChangeEvent(order.getId(), OrderStatus.ACCEPTED));
        return this.orderRepository.save(order);
    }
}
