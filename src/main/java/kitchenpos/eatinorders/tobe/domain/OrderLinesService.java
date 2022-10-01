package kitchenpos.eatinorders.tobe.domain;


import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.tobe.domain.exception.ConsistencyMenuException;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderLineItemPrice;
import kitchenpos.eatinorders.tobe.domain.vo.DisplayedMenu;
import kitchenpos.eatinorders.tobe.dto.MenuDTO;
import kitchenpos.global.vo.Price;

public class OrderLinesService {

    private final MenuServerClient menuServerClient;

    public OrderLinesService(MenuServerClient menuServerClient) {
        this.menuServerClient = menuServerClient;
    }

    public void syncMenu(EatInOrder order) {
        OrderLineItems items = order.getOrderLineItems();
        List<OrderLineItem> requestItems = items.getValues();
        List<MenuDTO> menus = findAllByIds(requestItems);
        validateRequestMenus(requestItems, menus);

        Map<UUID, DisplayedMenu> mapper = convertToMap(menus);
        for (OrderLineItem item : requestItems) {
            UUID menuId = item.getMenuId();
            DisplayedMenu menu = mapper.get(menuId);

            validateOrderPrice(item, menu.getPrice());
            item.withMenu(menu);
        }
    }

    private void validateRequestMenus(List<OrderLineItem> requestItems, List<MenuDTO> menus) {
        if (menus.size() != requestItems.size()) {
            throw new ConsistencyMenuException();
        }
    }

    private void validateOrderPrice(OrderLineItem requestItem, Price menuPrice) {
        if (!requestItem.samePrice(menuPrice)) {
            throw new InvalidOrderLineItemPrice();
        }
    }

    private List<MenuDTO> findAllByIds(List<OrderLineItem> items) {
        List<UUID> menuIds = items.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toList());

        return menuServerClient.findDisplayedMenuAllByIds(menuIds);
    }

    private Map<UUID, DisplayedMenu> convertToMap(List<MenuDTO> menus) {
        return menus.stream()
                .map(MenuDTO::toMenu)
                .collect(collectingAndThen(
                        toMap(DisplayedMenu::getId, Function.identity()),
                        Collections::unmodifiableMap)
                );
    }
}
