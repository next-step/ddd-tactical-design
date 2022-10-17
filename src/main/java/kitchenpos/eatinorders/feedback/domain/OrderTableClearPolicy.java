package kitchenpos.eatinorders.feedback.domain;

import org.springframework.stereotype.Component;

@Component
public class OrderTableClearPolicy {
    private final EatInOrderRepository eatInOrderRepository;

    public OrderTableClearPolicy(EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    public boolean canClear(Long orderTableId) {
        return !eatInOrderRepository.existsByOrderTableIdAndStatusNot(orderTableId, OrderStatus.COMPLETED);
    }
}
