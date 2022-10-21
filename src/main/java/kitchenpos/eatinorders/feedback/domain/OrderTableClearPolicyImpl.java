package kitchenpos.eatinorders.feedback.domain;

import org.springframework.stereotype.Component;

@Component
public class OrderTableClearPolicyImpl implements OrderTableClearPolicy {
    private final EatInOrderRepository eatInOrderRepository;

    public OrderTableClearPolicyImpl(EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    public boolean canClear(Long orderTableId) {
        return !eatInOrderRepository.existsByOrderTableIdAndStatusNot(orderTableId, OrderStatus.COMPLETED);
    }
}
