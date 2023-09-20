package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderTable;
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
