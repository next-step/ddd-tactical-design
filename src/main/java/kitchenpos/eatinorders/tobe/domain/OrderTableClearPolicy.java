package kitchenpos.eatinorders.tobe.domain;

import org.springframework.stereotype.Component;

@Component
public class OrderTableClearPolicy {
    private final EatInOrderRepository eatInOrderRepository;

    public OrderTableClearPolicy(EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    public void clear(OrderTable orderTable) {
        if (eatInOrderRepository.existsByOrderTableIdAndStatusNot(orderTable.getId(), OrderStatus.COMPLETED)) {
            throw new IllegalStateException("완료되지 않는 주문이 남아있는 경우 테이블을 치울 수 없습니다.");
        }
        orderTable.clear();
    }
}
