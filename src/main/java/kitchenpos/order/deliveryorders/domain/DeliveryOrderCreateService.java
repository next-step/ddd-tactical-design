package kitchenpos.order.deliveryorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItemsService;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderLineItems;
import kitchenpos.order.supports.factory.OrderCreateFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
        return orderRepository.save(OrderCreateFactory.deliveryOrder(getOrderLineItems(order), order.getDeliveryAddress()));
    }

    private OrderLineItems getOrderLineItems(Order order) {
        return orderLineItemsService.getOrderLineItems(order.getOrderLineItems().getOrderLineItems());
    }

    private void validDeliveryAddress(Order order) {
        final String deliveryAddress = order.getDeliveryAddress();
        if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
