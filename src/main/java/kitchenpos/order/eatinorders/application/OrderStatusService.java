package kitchenpos.order.eatinorders.application;

import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.domain.OrderTable;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusService {

    private final OrderRepository orderRepository;

    public OrderStatusService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public boolean existsByOrderTableAndStatusNot(final OrderTable orderTable, final OrderStatus orderStatus) {
        return orderRepository.existsByOrderTableAndStatusNot(orderTable, orderStatus);
    }
}
