package kitchenpos.order.takeoutorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItemsValidService;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.supports.factory.OrderCreateFactory;
import org.springframework.stereotype.Component;

@Component
public class TakeOutOrderCreateService {

    private final OrderRepository orderRepository;
    private final OrderLineItemsValidService orderLineItemsValidService;

    public TakeOutOrderCreateService(OrderRepository orderRepository, OrderLineItemsValidService orderLineItemsValidService) {
        this.orderRepository = orderRepository;
        this.orderLineItemsValidService = orderLineItemsValidService;
    }

    public Order create(Order order) {
        orderLineItemsValidService.valid(order.getOrderLineItems());
        return orderRepository.save(OrderCreateFactory.takeOutOrder(order));
    }

}
