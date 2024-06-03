package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.todo.domain.orders.EatInOrderRepository;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderStatus;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderClient;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;
import org.springframework.stereotype.Component;

@Component
public class OrderClientImpl implements OrderClient {
    private final EatInOrderRepository orderRepository;

    public OrderClientImpl(EatInOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public boolean containsInvalidOrderForClearOrderTable(OrderTable orderTable) {
        return orderRepository.existsByOrderTableAndStatusNot(orderTable, EatInOrderStatus.COMPLETED);
    }
}
