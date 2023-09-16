package kitchenpos.eatinorders.tobe.domain;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.domain.event.OrderCreateRequested;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class OrderMenuService {

    private final ApplicationEventPublisher eventPublisher;
    private final OrderRepository orderRepository;

    public OrderMenuService(ApplicationEventPublisher eventPublisher, OrderRepository orderRepository) {
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(OrderTable orderTable, OrderLineItems orderLineItems) {
        List<UUID> menuIds = orderLineItems.getOrderLineItems()
            .stream()
            .map(OrderLineItem::getMenuId)
            .collect(Collectors.toList());

        OrderCreateRequested orderCreateRequested = new OrderCreateRequested(menuIds);
        eventPublisher.publishEvent(orderCreateRequested);

        return orderRepository.save(new Order(orderTable, orderLineItems));
    }

}
