package kitchenpos.order.domain;

import kitchenpos.order.supports.factory.OrderCreateFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DeliveryOrderCreateService {

    private final OrderLineItemsValidService orderLineItemsValidService;

    public DeliveryOrderCreateService(OrderLineItemsValidService orderLineItemsValidService) {
        this.orderLineItemsValidService = orderLineItemsValidService;
    }

    public Order create(Order order) {
        validDeliveryAddress(order.getDeliveryAddress());
        orderLineItemsValidService.valid(order.getOrderLineItems());
        return OrderCreateFactory.deliveryOrder(order, order.getDeliveryAddress());
    }

    private void validDeliveryAddress(String deliveryAddress) {
        if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
