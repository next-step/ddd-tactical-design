package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderMenus;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.translator.OrderMenuTranslator;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;

import java.util.*;

public class OrderCreateValidator implements Validator<Order> {

    private final OrderTableRepository orderTableRepository;

    private final OrderMenuTranslator orderMenuTranslator;

    public OrderCreateValidator(
            final OrderTableRepository orderTableRepository,
            final OrderMenuTranslator orderMenuTranslator
    ) {
        this.orderTableRepository = orderTableRepository;
        this.orderMenuTranslator = orderMenuTranslator;
    }

    @Override
    public void validate(final Order order) {
        final OrderTable orderTable = orderTableRepository.findById(order.getOrderTableId())
                .orElseThrow(() -> new NoSuchElementException("등록된 주문 테이블이 없습니다."));
        if (orderTable.isEmpty()) {
            throw new IllegalStateException("빈 테이블에는 주문을 등록할 수 없습니다.");
        }

        final List<UUID> menuIds = order.getMenuIds();
        final OrderMenus orderMenus = new OrderMenus(orderMenuTranslator.findAllByIdIn(menuIds));
        orderMenus.validate(menuIds);

        order.validateOrderPrice(orderMenus);
    }
}
