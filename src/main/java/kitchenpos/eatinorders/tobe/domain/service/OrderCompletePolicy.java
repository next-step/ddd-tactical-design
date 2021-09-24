package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.commons.tobe.domain.service.Policy;
import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;

import java.util.NoSuchElementException;

public class OrderCompletePolicy implements Policy<Order> {

    private final OrderTableRepository orderTableRepository;

    private final Validator<OrderTable> orderTableClearValidator;

    public OrderCompletePolicy(
            final OrderTableRepository orderTableRepository,
            final Validator<OrderTable> orderTableClearValidator) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableClearValidator = orderTableClearValidator;
    }

    @Override
    public void enforce(final Order order) {
        final OrderTable orderTable = orderTableRepository.findById(order.getOrderTableId())
                .orElseThrow(() -> new NoSuchElementException("등록된 주문 테이블이 없습니다."));
        orderTable.clear(orderTableClearValidator);
    }
}
