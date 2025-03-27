package kitchenpos.eatinorders.tobe.domain.orderTable;

import kitchenpos.eatinorders.tobe.domain.exception.InvalidEatInOrderTableException;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultOrderTableOrders implements OrderTableOrders {

    private final EatInOrderRepository eatInOrderRepository;
    private final EatInOrderTableRepository eatInOrderTableRepository;

    public DefaultOrderTableOrders(final EatInOrderRepository eatInOrderRepository,
                                   final EatInOrderTableRepository eatInOrderTableRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.eatInOrderTableRepository = eatInOrderTableRepository;
    }

    @Override
    public boolean existByOrderTableId(final UUID orderTableId) {
        final EatInOrderTable orderTable = eatInOrderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new InvalidEatInOrderTableException("주문 테이블이 존재해야 합니다."));

        return eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, EatInOrderStatus.COMPLETED);
    }
}
