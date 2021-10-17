package kitchenpos.eatinorders.tobe.domain.model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.menu.domain.model.Menu;
import kitchenpos.menus.tobe.menu.domain.repository.MenuRepository;
import org.springframework.stereotype.Component;

@Component
public class MenuDomainService {
    private final MenuRepository menuRepository;

    public MenuDomainService(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void validateOrder(final Order order) {
        final List<OrderLineItem> orderLineItems = order.getOrderLineItems().getOrderLineItems();
        final List<Menu> menus = menuRepository.findAllByIdIn(orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toList()));
        menus.forEach(this::validateMenu);
        if (menus.size() != orderLineItems.size()) {
            throw new IllegalArgumentException("부적절한 메뉴는 주문할 수 없습니다.");
        }
    }

    private void validateMenu(final Menu menu) {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException("숨겨진 메뉴입니다.");
        }
    }

    private Menu getMenu(final OrderLineItem orderLineItem) {
        final Menu menu = menuRepository.findById(orderLineItem.getMenuId())
            .orElseThrow(NoSuchElementException::new);
        validateMenu(menu);
        if (menu.getMenuPrice().isNotEqualTo(orderLineItem.getPrice())) {
            throw new IllegalArgumentException("메뉴의 가격과 주문 항목의 가격은 같아야 합니다.");
        }
        return menu;
    }
}
