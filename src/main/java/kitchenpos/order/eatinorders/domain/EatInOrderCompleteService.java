package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.event.OrderStatusChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EatInOrderCompleteService {

    private final OrderRepository orderRepository;
    private final OrderTableClearService orderTableClearService;
    private final ApplicationEventPublisher publisher;

    public EatInOrderCompleteService(OrderRepository orderRepository, OrderTableClearService orderTableClearService, ApplicationEventPublisher publisher) {
        this.orderRepository = orderRepository;
        this.orderTableClearService = orderTableClearService;
        this.publisher = publisher;
    }

    public Order complete(Order order) {
        final OrderStatus status = order.getStatus();
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        this.publisher.publishEvent(new OrderStatusChangeEvent(order.getId(), OrderStatus.COMPLETED));
        this.orderTableClearService.clear(order.getOrderTable());
        return orderRepository.save(order);
    }
}
