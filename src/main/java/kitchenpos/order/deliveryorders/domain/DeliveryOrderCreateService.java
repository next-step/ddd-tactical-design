package kitchenpos.order.deliveryorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItemsValidService;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.supports.factory.OrderCreateFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DeliveryOrderCreateService {

    private final OrderLineItemsValidService orderLineItemsValidService;
    private final OrderRepository orderRepository;

    public DeliveryOrderCreateService(OrderRepository orderRepository, OrderLineItemsValidService orderLineItemsValidService) {
        this.orderLineItemsValidService = orderLineItemsValidService;
        this.orderRepository = orderRepository;
    }

    public Order create(Order requestOrder) {
        validDeliveryAddress(requestOrder.getDeliveryAddress());
        orderLineItemsValidService.valid(requestOrder.getOrderLineItems());
        Order order = OrderCreateFactory.deliveryOrder(requestOrder, requestOrder.getDeliveryAddress());
        return orderRepository.save(order);
    }

    private void validDeliveryAddress(String deliveryAddress) {
        if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
