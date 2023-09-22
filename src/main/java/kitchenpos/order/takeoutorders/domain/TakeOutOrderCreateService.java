package kitchenpos.order.takeoutorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItemsService;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderLineItems;
import kitchenpos.order.supports.factory.OrderCreateFactory;
import org.springframework.stereotype.Component;

@Component
public class TakeOutOrderCreateService {

    private final OrderRepository orderRepository;
    private final OrderLineItemsService orderLineItemsService;

    public TakeOutOrderCreateService(OrderRepository orderRepository, OrderLineItemsService orderLineItemsService) {
        this.orderRepository = orderRepository;
        this.orderLineItemsService = orderLineItemsService;
    }

    public Order create(Order order) {
        return orderRepository.save(OrderCreateFactory.takeOutOrder(getOrderLineItems(order)));
    }

    private OrderLineItems getOrderLineItems(Order order) {
        return orderLineItemsService.getOrderLineItems(order.getOrderLineItems().getOrderLineItems());
    }
}
