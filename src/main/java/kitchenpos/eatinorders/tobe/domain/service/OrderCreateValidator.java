package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.menus.tobe.domain.model.Menus;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;

import java.util.*;

public class OrderCreateValidator implements Validator<Order> {

    private final OrderTableRepository orderTableRepository;

    private final MenuRepository menuRepository;

    public OrderCreateValidator(
            final OrderTableRepository orderTableRepository,
            final MenuRepository menuRepository
    ) {
        this.orderTableRepository = orderTableRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public void validate(final Order order) {
        final OrderTable orderTable = orderTableRepository.findById(order.getOrderTableId())
                .orElseThrow(() -> new NoSuchElementException("등록된 주문 테이블이 없습니다."));
        if (orderTable.isEmpty()) {
            throw new IllegalStateException("빈 테이블에는 주문을 등록할 수 없습니다.");
        }

        final List<UUID> menuIds = order.getMenuIds();
        final Menus menus = new Menus(menuRepository.findAllByIdIn(menuIds));
        if (!menus.isSizeEqual(menuIds.size())) {
            throw new IllegalArgumentException("등록된 메뉴가 없습니다.");
        }
        if (menus.existsMenuNotDisplay()) {
            throw new IllegalStateException("노출되지 않은 메뉴는 주문할 수 없습니다.");
        }

        order.validateOrderPrice(menus.getPrices());
    }
}
