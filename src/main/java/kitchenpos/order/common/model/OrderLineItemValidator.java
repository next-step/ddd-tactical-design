package kitchenpos.order.common.model;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.order.common.exception.OrderLineItemExceptionMessage;
import org.springframework.stereotype.Service;

@Service
public class OrderLineItemValidator {

    private final MenuRepository menuRepository;

    public OrderLineItemValidator(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void validate(List<OrderLineItem> orderLineItems) {
        final List<UUID> menuIds = findMenuIdsBy(orderLineItems);
        final List<Menu> menus = menuRepository.findAllByIdIn(menuIds);
        validateMenuExists(orderLineItems, menus);
    }

    private List<UUID> findMenuIdsBy(List<OrderLineItem> orderLineItems) {
        return orderLineItems.stream()
                .map(OrderLineItem::getMenuId)
                .toList();
    }

    private void validateMenuExists(List<OrderLineItem> orderLineItems, List<Menu> menus) {
        if (menus.size() != orderLineItems.size()) {
            throw new IllegalArgumentException(
                    OrderLineItemExceptionMessage.ORDER_LINE_ITEM_INCORRECT_MENU_INFO_EXCEPTION.getMessage());
        }
    }
}
