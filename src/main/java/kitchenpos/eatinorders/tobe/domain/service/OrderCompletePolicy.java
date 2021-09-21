package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.commons.tobe.domain.service.Policy;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;

public class OrderCompletePolicy implements Policy<Order> {

    public OrderCompletePolicy(final OrderTableRepository orderTableRepository) {
    }

    @Override
    public void enforce(final Order order) {
    }
}
