package kitchenpos.eatinorders.order.tobe.domain;

import kitchenpos.common.annotation.DomainService;

import java.util.UUID;

@DomainService
public class EatInOrderCompletePolicy {

    private final EatInOrderRepository eatInOrderRepository;
    private final CleanUp cleanUp;

    public EatInOrderCompletePolicy(EatInOrderRepository eatInOrderRepository, CleanUp cleanUp) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.cleanUp = cleanUp;
    }

    boolean isClearOrderTableCondition(final UUID orderTableId) {
        return !eatInOrderRepository.existsByOrderTableAndStatusNot(orderTableId, EatInOrderStatus.COMPLETED);
    }

    void clearOrderTable(final UUID orderTableId) {
        cleanUp.clear(orderTableId, eatInOrderRepository);
    }
}
