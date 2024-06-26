package kitchenpos.order.tobe.eatinorder.domain.validate;

import kitchenpos.order.tobe.eatinorder.domain.EatInOrderLineItem;
import kitchenpos.order.tobe.eatinorder.domain.MenuClient;
import kitchenpos.order.tobe.eatinorder.domain.MenuDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MenuValidator {
    private final MenuClient menuClient;

    public MenuValidator(MenuClient menuClient) {
        this.menuClient = menuClient;
    }

    public void validateOrderLineItems(List<EatInOrderLineItem> orderLineItems) {
        Map<UUID, EatInOrderLineItem> orderLineItemMap = orderLineItems.stream()
                .collect(Collectors.toMap(EatInOrderLineItem::getMenuId, Function.identity()));

        List<MenuDto> menus = menuClient.findMenusByIds(orderLineItemMap.keySet().stream().toList());

        validateMenuExistence(orderLineItemMap, menus);
        validateMenuDisplayedAndPrice(orderLineItemMap, menus);
    }

    private void validateMenuExistence(Map<UUID, EatInOrderLineItem> orderLineItemMap, List<MenuDto> menus) {
        if (menus.size() != orderLineItemMap.size()) {
            throw new IllegalArgumentException("주문에 속한 메뉴의 갯수가 실제 메뉴와 일치하지 않습니다.");
        }
    }

    private void validateMenuDisplayedAndPrice(Map<UUID, EatInOrderLineItem> orderLineItemMap, List<MenuDto> menus) {
        for (MenuDto menu : menus) {
            EatInOrderLineItem orderLineItem = orderLineItemMap.get(menu.id());

            if (!menu.isDisplayed()) {
                throw new IllegalStateException("메뉴가 노출되지 않았습니다.");
            }

            if (menu.price().compareTo(orderLineItem.getPrice()) != 0) {
                throw new IllegalArgumentException("메뉴의 가격이 일치하지 않습니다.");
            }
        }
    }
}
