package kitchenpos.eatinorders.order.tobe.domain;

import kitchenpos.common.annotation.DomainService;
import kitchenpos.eatinorders.ordertable.tobe.domain.CleanUpPolicy;

import java.util.UUID;

@DomainService
public class OrderTableCleanUpPolicy implements CleanUpPolicy {

    private final EatInOrderRepository eatInOrderRepository;

    public OrderTableCleanUpPolicy(final EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    @Override
    public boolean isCleanUpCondition(final UUID orderTableId) {
        return !eatInOrderRepository.existsByOrderTableAndStatusNot(orderTableId, EatInOrderStatus.COMPLETED);
    }
}
