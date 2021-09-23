package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;

import java.util.*;
import java.util.function.Predicate;

public class OrderCreateValidator implements Validator<Order> {

    private final OrderTableRepository orderTableRepository;

    private final MenuRepository menuRepository;

    public OrderCreateValidator(final OrderTableRepository orderTableRepository, final MenuRepository menuRepository) {
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
        final List<Menu> menus = menuRepository.findAllByIdIn(menuIds);
        if (menuIds.size() != menus.size()) {
            throw new IllegalArgumentException("등록된 메뉴가 없습니다.");
        }

        final boolean existsMenuNotDisplay = menus.stream()
                .anyMatch(Predicate.not(Menu::isDisplayed));
        if (existsMenuNotDisplay) {
            throw new IllegalStateException("노출되지 않은 메뉴는 주문할 수 없습니다.");
        }

        order.validateOrderPrice(menus);
    }
}
