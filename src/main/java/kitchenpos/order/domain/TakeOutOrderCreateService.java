package kitchenpos.order.domain;

import kitchenpos.order.supports.factory.OrderCreateFactory;
import org.springframework.stereotype.Service;

@Service
public class TakeOutOrderCreateService {
    private final OrderLineItemsValidService orderLineItemsValidService;

    public TakeOutOrderCreateService(OrderLineItemsValidService orderLineItemsValidService) {
        this.orderLineItemsValidService = orderLineItemsValidService;
    }

    public Order create(Order order) {
        orderLineItemsValidService.valid(order.getOrderLineItems());
        return OrderCreateFactory.takeOutOrder(order);
    }


}
