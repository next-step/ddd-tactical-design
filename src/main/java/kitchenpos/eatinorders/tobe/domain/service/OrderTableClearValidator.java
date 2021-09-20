package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.OrderRepository;

public class OrderTableClearValidator implements Validator<OrderTable> {

    public OrderTableClearValidator(final OrderRepository orderRepository) {
    }

    @Override
    public void validate(final OrderTable orderTable) {
    }
}
