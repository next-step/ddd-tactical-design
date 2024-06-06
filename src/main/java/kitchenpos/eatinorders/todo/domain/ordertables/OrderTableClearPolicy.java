package kitchenpos.eatinorders.todo.domain.ordertables;

import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderRepository;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.ORDER_TABLE_CONTAINS_INVALID_ORDER;

@Component
public class OrderTableClearPolicy {
    private final EatInOrderRepository orderRepository;

    public OrderTableClearPolicy(final EatInOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void checkClear(UUID orderTableId) {
        if (orderRepository.existsByOrderTableIdAndStatusNot(orderTableId, EatInOrderStatus.COMPLETED)) {
            throw new KitchenPosIllegalStateException(ORDER_TABLE_CONTAINS_INVALID_ORDER, orderTableId);
        }
    }
}
