package kitchenpos.eatinorders.tobe.domain.order;

import java.util.List;
import java.util.NoSuchElementException;
import kitchenpos.common.domain.MenuId;
import kitchenpos.common.domain.OrderTableId;
import kitchenpos.common.domain.Validator;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;

public class OrderValidator implements Validator<Order> {

    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;

    public OrderValidator(
        final MenuRepository menuRepository,
        final OrderTableRepository orderTableRepository
    ) {
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
    }

    public void validate(Order order) {
        final List<MenuId> menuIds = order.getMenuIds();
        orderShouldConsistOfAtLeastOneMenu(menuIds);

        final List<Menu> menus = menuRepository.findAllByIdIn(menuIds);
        orderShouldConsistOfRegisteredMenu(menuIds, menus);

        menus.forEach(menu -> {
            pricesShouldMatch(order, menu);
            menuShouldNotBeHidden(menu);
        });

        validateOrderTable(order.getOrderTableId());
    }

    private void orderShouldConsistOfAtLeastOneMenu(List<MenuId> menuIds) {
        if (menuIds.isEmpty()) {
            throw new IllegalArgumentException("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있습니다.");
        }
    }

    private void orderShouldConsistOfRegisteredMenu(List<MenuId> menuIds, List<Menu> menus) {
        if (menuIds.size() != menus.size()) {
            throw new NoSuchElementException("메뉴가 없으면 매장 주문을 등록할 수 없습니다.");
        }
    }

    private void menuShouldNotBeHidden(final Menu menu) {
        if (menu.isHidden()) {
            throw new IllegalStateException("숨겨진 메뉴는 주문할 수 없습니다.");
        }
    }

    private void pricesShouldMatch(final Order order, final Menu menu) {
        if (order.isPriceValid(menu.getId(), menu.getPrice())) {
            throw new IllegalArgumentException("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 합니다.");
        }
    }

    private void validateOrderTable(final OrderTableId orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);

        if (orderTable.isEmpty()) {
            throw new IllegalStateException("빈 테이블에는 매장 주문을 등록할 수 없습니다.");
        }
    }
}
