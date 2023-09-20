package kitchenpos.takeoutorders.domain;

import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.domain.vo.OrderLineItems;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TakeOutOrderCreateService {

    private final OrderRepository orderRepository;
    private final OrderLineItemsService orderLineItemsService;

    public TakeOutOrderCreateService(OrderRepository orderRepository, OrderLineItemsService orderLineItemsService) {
        this.orderRepository = orderRepository;
        this.orderLineItemsService = orderLineItemsService;
    }

    public Order create(Order order) {
        Order eatInOrder = new Order(UUID.randomUUID(), OrderType.TAKEOUT, OrderStatus.WAITING, LocalDateTime.now(), getOrderLineItems(order), null, null, null);
        return orderRepository.save(eatInOrder);
    }

    private OrderLineItems getOrderLineItems(Order order) {
        return orderLineItemsService.getOrderLineItems(order.getOrderLineItems().getOrderLineItems(), order.getType());
    }
}
