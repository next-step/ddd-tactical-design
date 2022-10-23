package kitchenpos.eatinorders.order.tobe.domain;

import kitchenpos.common.annotation.DomainService;
import kitchenpos.eatinorders.ordertable.tobe.domain.CleanUpPolicy;

import java.util.UUID;

@DomainService
public class LastEatInOrderPolicy implements CleanUpPolicy {

    private final EatInOrderRepository eatInOrderRepository;

    public LastEatInOrderPolicy(final EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    @Override
    public boolean isMatchCondition(final UUID orderTableId) {
        return !eatInOrderRepository.existsByOrderTableAndStatusNot(orderTableId, EatInOrderStatus.COMPLETED);
    }
}
