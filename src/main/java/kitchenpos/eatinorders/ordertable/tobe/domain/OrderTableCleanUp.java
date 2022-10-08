package kitchenpos.eatinorders.ordertable.tobe.domain;

import kitchenpos.common.annotation.DomainService;
import kitchenpos.eatinorders.order.tobe.domain.CleanUp;
import kitchenpos.eatinorders.order.tobe.domain.EatInOrderRepository;
import kitchenpos.eatinorders.order.tobe.domain.EatInOrderStatus;

import java.util.UUID;

@DomainService
public class OrderTableCleanUp implements CleanUp {

    private final OrderTableRepository orderTableRepository;

    public OrderTableCleanUp(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public void clear(final UUID orderTableId, final EatInOrderRepository eatInOrderRepository) {
        if (eatInOrderRepository.existsByOrderTableAndStatusNot(orderTableId, EatInOrderStatus.COMPLETED)) {
            throw new IllegalArgumentException("완료되지 않은 주문이 있습니다.");
        }
        final OrderTable orderTable = orderTableRepository.findById(orderTableId).orElseThrow(IllegalArgumentException::new);
        orderTable.clear();
    }
}
