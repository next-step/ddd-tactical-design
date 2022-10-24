package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderLineException;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderLineException.*;

@Component
public class OrderPolicy {
    private final MenuRepository menuRepository;

    public OrderPolicy(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void validateOrderLineItems(List<OrderLineItem> orderLineItems) {
        List<UUID> menuIds = orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .collect(Collectors.toList());

        List<Menu> menus = menuRepository.findAllByIdIn(menuIds);

        if (menus.size() != orderLineItems.size()) {
            throw new IllegalOrderLineException(NO_MENU);
        }

        menus.forEach(menu -> {
            if (!menu.isDisplayed()) {
                throw new IllegalOrderLineException(NOT_DISPLAYED);
            }

            if (!findMatchingOrderLineItem(orderLineItems, menu).priceEq(menu)) {
                throw new IllegalOrderLineException(PRICE_MISMATCH);
            }
        });
    }

    private OrderLineItem findMatchingOrderLineItem(List<OrderLineItem> orderLineItems, Menu menu) {
        return orderLineItems
                .stream()
                .filter(i -> i.getMenuId().equals(menu.getId()))
                .findAny()
                .orElseThrow();
    }

}
