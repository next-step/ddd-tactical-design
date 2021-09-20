package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Completed;
import kitchenpos.eatinorders.tobe.domain.repository.OrderRepository;

public class OrderTableClearValidator implements Validator<OrderTable> {

    private final OrderRepository orderRepository;

    public OrderTableClearValidator(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void validate(final OrderTable orderTable) {
        if (orderRepository.existsByOrderTableAndStatusNot(orderTable, new Completed())) {
            throw new IllegalStateException("계산 완료되지 않은 주문이 있습니다.");
        }
    }
}
