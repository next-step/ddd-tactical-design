package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderClient;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;
import org.springframework.stereotype.Component;

@Component
public class OrderClientImpl implements OrderClient {
    private final OrderRepository orderRepository;

    public OrderClientImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public boolean containsInvalidOrderForClearOrderTable(OrderTable orderTable) {
        return orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED);
    }
}
