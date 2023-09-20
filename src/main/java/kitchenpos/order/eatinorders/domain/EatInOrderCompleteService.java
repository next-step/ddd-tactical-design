package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class EatInOrderCompleteService {

    private final OrderRepository orderRepository;
    private final OrderTableClearService orderTableClearService;

    public EatInOrderCompleteService(OrderRepository orderRepository, OrderTableClearService orderTableClearService) {
        this.orderRepository = orderRepository;
        this.orderTableClearService = orderTableClearService;
    }

    public Order complete(Order order) {
        final OrderStatus status = order.getStatus();
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        order.setStatus(OrderStatus.COMPLETED);
        orderTableClearService.clear(order.getOrderTable());
        return orderRepository.save(order);
    }
}
