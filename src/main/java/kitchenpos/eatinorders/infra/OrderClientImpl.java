package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.todo.domain.orders.EatInOrderRepository;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderStatus;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderClient;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderClientImpl implements OrderClient {
    private final EatInOrderRepository orderRepository;

    public OrderClientImpl(EatInOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public boolean containsInvalidOrderForClearOrderTable(UUID orderTableId) {
        return orderRepository.existsByOrderTableIdAndStatusNot(orderTableId, EatInOrderStatus.COMPLETED);
    }
}
