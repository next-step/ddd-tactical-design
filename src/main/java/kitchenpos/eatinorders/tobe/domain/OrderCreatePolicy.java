package kitchenpos.eatinorders.tobe.domain;

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

    public EatInOrder create(List<OrderLineItem> orderLineItems, Long orderTableId) {
        validateOrderTable(orderTableId);
        validateOrderPrice(orderLineItems);
        return new EatInOrder(orderLineItems, orderTableId);
    }

    private void validateOrderTable(Long orderTableId) {
        OrderTable orderTable = orderTableRepository.findById(orderTableId).orElseThrow();
        if (!orderTable.isOccupied()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateOrderPrice(List<OrderLineItem> orderLineItems) {
        List<Long> menuIds = orderLineItems.stream().map(OrderLineItem::getMenuId).collect(Collectors.toList());
        List<Menu> menus = menuRepository.findAllByIdIn(menuIds);

        for (Menu menu : menus) {
            if (!menu.isDisplayed()) {
                throw new IllegalArgumentException("전시된 메뉴만 주문할 수 있습니다.");
            }

            OrderLineItem orderLineItem = findByMenuId(orderLineItems, menu.getId());
            if (!orderLineItem.getPrice().equals(menu.getPrice())) {
                throw new IllegalArgumentException("주문 상품의 가격은 메뉴의 가격과 같아야 합니다.");
            }
        }
    }

    private OrderLineItem findByMenuId(List<OrderLineItem> orderLineItems, Long menuId) {
        return orderLineItems.stream()
                .filter(orderLineItem -> orderLineItem.getMenuId().equals(menuId))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
