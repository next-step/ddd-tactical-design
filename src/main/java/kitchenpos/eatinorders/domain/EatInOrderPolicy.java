package kitchenpos.eatinorders.domain;

import org.springframework.stereotype.Component;

@Component
public class EatInOrderPolicy {
    private final OrderRepository orderRepository;

    public EatInOrderPolicy(OrderRepository eatInOrderRepository) {
        this.orderRepository = eatInOrderRepository;
    }

    public void follow(OrderTable orderTable) {
        if (!orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            orderTable.clear();
        }
    }
}
