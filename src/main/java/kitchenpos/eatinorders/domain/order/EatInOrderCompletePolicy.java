package kitchenpos.eatinorders.domain.order;

import org.springframework.stereotype.Component;

import kitchenpos.eatinorders.ordertable.domain.OrderTable;

@Component
public class EatInOrderCompletePolicy {
    private final EatInOrderRepository eatInOrderRepository;

    public EatInOrderCompletePolicy(EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    public void follow(OrderTable orderTable) {
        if (!eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            orderTable.clean();
        }
    }
}
