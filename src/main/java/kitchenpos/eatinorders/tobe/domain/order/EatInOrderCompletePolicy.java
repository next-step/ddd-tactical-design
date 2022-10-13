package kitchenpos.eatinorders.tobe.domain.order;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EatInOrderCompletePolicy {

    private final EatInOrderRepository eatInOrderRepository;
    private final ClearPolicy clearPolicy;

    public EatInOrderCompletePolicy(EatInOrderRepository eatInOrderRepository, ClearPolicy clearPolicy) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.clearPolicy = clearPolicy;
    }

    boolean isClearOrderTableCompleted(UUID orderTableId) {
        return !eatInOrderRepository.existsByOrderTableIdAndStatusNot(orderTableId, OrderStatus.COMPLETED);
    }

    void clearOrderTable(UUID orderTableId) {
        clearPolicy.clear(orderTableId, eatInOrderRepository);
    }
}
