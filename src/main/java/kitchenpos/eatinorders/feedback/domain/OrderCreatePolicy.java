package kitchenpos.eatinorders.feedback.domain;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderCreatePolicy {
    private final OrderTableRepository orderTableRepository;
    private final MenuRepository menuRepository;

    public OrderCreatePolicy(OrderTableRepository orderTableRepository, MenuRepository menuRepository) {
        this.orderTableRepository = orderTableRepository;
        this.menuRepository = menuRepository;
    }

    public void validate(List<OrderLineItem> orderLineItems, Long orderTableId) {
        validateOrderTable(orderTableId);
        validateOrderLineItems(orderLineItems);
    }

    private void validateOrderTable(Long orderTableId) {
        OrderTable orderTable = orderTableRepository.findById(orderTableId).orElseThrow();
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException("사용중이지 않은 테이블에 매장 주문을 할 수 없습니다.");
        }
    }

    private void validateOrderLineItems(List<OrderLineItem> orderLineItems) {
        List<Menu> menus = findAllMenus(orderLineItems);
        orderLineItems.forEach(orderLineItem -> validateDisplayedAndPrice(orderLineItem, menus));
    }

    private void validateDisplayedAndPrice(OrderLineItem orderLineItem, List<Menu> menus) {
        Menu menu = findById(menus, orderLineItem.getMenuId());
        if (!menu.isDisplayed()) {
            throw new IllegalStateException("공개된 메뉴만 주문할 수 있습니다.");
        }

        if (!orderLineItem.getPrice().equals(menu.getPrice())) {
            throw new IllegalArgumentException("주문의 가격과 메뉴의 가격이 일치해야 합니다.");
        }
    }

    private List<Menu> findAllMenus(List<OrderLineItem> orderLineItems) {
        List<Long> menuIds = orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toUnmodifiableList());
        return menuRepository.findAllByIdIn(menuIds);
    }

    private Menu findById(List<Menu> menus, Long menuId) {
        return menus.stream()
                .filter(menu -> menu.equalsId(menuId))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
