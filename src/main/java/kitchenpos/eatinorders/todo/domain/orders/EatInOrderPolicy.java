package kitchenpos.eatinorders.todo.domain.orders;

import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EatInOrderPolicy {
    private EatInOrderRepository orderRepository;
    private OrderTableClient orderTableClient;

    public EatInOrderPolicy(EatInOrderRepository orderRepository, OrderTableClient orderTableClient) {
        this.orderRepository = orderRepository;
        this.orderTableClient = orderTableClient;
    }

    public void clearOrderTable(UUID orderTableId) {
        final OrderTable orderTable = orderTableClient.getOrderTable(orderTableId);
        if (!orderRepository.existsByOrderTableIdAndStatusNot(orderTableId, EatInOrderStatus.COMPLETED)) {
            orderTable.clear();
        }
    }
}
