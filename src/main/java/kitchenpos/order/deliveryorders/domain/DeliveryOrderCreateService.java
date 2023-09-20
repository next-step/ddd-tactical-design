package kitchenpos.order.deliveryorders.domain;

import kitchenpos.order.domain.*;
import kitchenpos.order.eatinorders.domain.vo.OrderLineItems;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Component
public class DeliveryOrderCreateService {

    private final OrderRepository orderRepository;
    private final OrderLineItemsService orderLineItemsService;

    public DeliveryOrderCreateService(OrderRepository orderRepository, OrderLineItemsService orderLineItemsService) {
        this.orderRepository = orderRepository;
        this.orderLineItemsService = orderLineItemsService;
    }

    public Order create(Order order) {
        validDeliveryAddress(order);
        Order eatInOrder = new Order(UUID.randomUUID(), OrderType.DELIVERY, OrderStatus.WAITING, LocalDateTime.now(), getOrderLineItems(order), order.getDeliveryAddress(), null, null);
        return orderRepository.save(eatInOrder);
    }

    private OrderLineItems getOrderLineItems(Order order) {
        return orderLineItemsService.getOrderLineItems(order.getOrderLineItems().getOrderLineItems(), order.getType());
    }

    private void validDeliveryAddress(Order order) {
        final String deliveryAddress = order.getDeliveryAddress();
        if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
