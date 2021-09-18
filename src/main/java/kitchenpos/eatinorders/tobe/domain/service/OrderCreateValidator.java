package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;

public class OrderCreateValidator implements Validator<Order> {

    public OrderCreateValidator(final OrderTableRepository orderTableRepository, final MenuRepository menuRepository) {
    }

    @Override
    public void validate(final Order order) {
    }
}
