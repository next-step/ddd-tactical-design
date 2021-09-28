package kitchenpos.eatinorders.tobe.domain.ordertable;

import kitchenpos.common.domain.Validator;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.order.OrderRepository;

public class OrderTableValidator implements Validator<OrderTable> {

    private final OrderRepository orderRepository;

    public OrderTableValidator(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void validate(final OrderTable orderTable) {
        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            throw new IllegalStateException("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없습니다.");
        }
    }
}
