package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableOrders;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;
import org.springframework.stereotype.Component;

@Component
public class DefaultOrderTableOrders implements OrderTableOrders {

    private final EatInOrderRepository eatInOrderRepository;

    public DefaultOrderTableOrders(final EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    @Override
    public boolean existByOrderTableId(final OrderTableId orderTableId) {
        return eatInOrderRepository.existsByOrderTableAndStatusNot(orderTableId, EatInOrderStatus.COMPLETED);
    }
}
