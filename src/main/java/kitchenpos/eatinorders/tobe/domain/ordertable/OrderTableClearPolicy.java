package kitchenpos.eatinorders.tobe.domain.ordertable;

import kitchenpos.eatinorders.tobe.domain.order.ClearPolicy;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.order.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderTableClearPolicy implements ClearPolicy {

    private final OrderTableRepository orderTableRepository;

    public OrderTableClearPolicy(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public void clear(UUID orderTableId, EatInOrderRepository eatInOrderRepository) {
        if (eatInOrderRepository.existsByOrderTableIdAndStatusNot(orderTableId, OrderStatus.COMPLETED)) {
            throw new IllegalStateException("완료되지 않은 주문이 있습니다.");
        }

        OrderTable orderTable = orderTableRepository.findById(orderTableId).orElseThrow(IllegalArgumentException::new);
        orderTable.tableClear();
    }
}
