package kitchenpos.order.domain;

import kitchenpos.order.supports.factory.OrderCreateFactory;

public class TakeOutOrderCreateService {
    private final OrderLineItemsService orderLineItemsService;

    public TakeOutOrderCreateService(OrderLineItemsService orderLineItemsService) {
        this.orderLineItemsService = orderLineItemsService;
    }

    public Order create(Order order) {
        return OrderCreateFactory.takeOutOrder(getOrderLineItems(order));
    }

    private OrderLineItems getOrderLineItems(Order order) {
        return orderLineItemsService.getOrderLineItems(order.getOrderLineItems().getOrderLineItems());
    }
}
