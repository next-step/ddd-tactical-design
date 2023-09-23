package kitchenpos.order.domain;

import kitchenpos.order.supports.factory.OrderCreateFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DeliveryOrderCreateService {

    private final OrderLineItemsService orderLineItemsService;

    public DeliveryOrderCreateService(OrderLineItemsService orderLineItemsService) {
        this.orderLineItemsService = orderLineItemsService;
    }

    public Order create(Order order) {
        validDeliveryAddress(order.getDeliveryAddress());
        return OrderCreateFactory.deliveryOrder(getOrderLineItems(order), order.getDeliveryAddress());
    }

    private OrderLineItems getOrderLineItems(Order order) {
        return orderLineItemsService.getOrderLineItems(order.getOrderLineItems().getOrderLineItems());
    }

    private void validDeliveryAddress(String deliveryAddress) {
        if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
