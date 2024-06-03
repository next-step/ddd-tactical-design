package kitchenpos.eatinorders.todo.domain.orders;

import kitchenpos.eatinorders.exception.KitchenPosIllegalArgumentException;
import kitchenpos.support.domain.MenuClient;
import kitchenpos.support.domain.OrderLineItem;

import java.util.List;
import java.util.UUID;

import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.INVALID_ORDER_LINE_ITEM_SIZE;

public class EatInOrderOrderLineItemsPolicy {
    private final MenuClient menuClient;

    public EatInOrderOrderLineItemsPolicy(MenuClient menuClient) {
        this.menuClient = menuClient;
    }

    public void checkDuplicatedMenu(List<OrderLineItem> orderLineItemRequests) {
        final int menuSize = menuClient.countMenusByIdIn(getMenuIds(orderLineItemRequests));
        int orderLineItemSize = orderLineItemRequests.size();
        if (menuSize != orderLineItemSize) {
            throw new KitchenPosIllegalArgumentException(INVALID_ORDER_LINE_ITEM_SIZE, menuSize, orderLineItemSize);
        }
    }

    private static List<UUID> getMenuIds(List<OrderLineItem> orderLineItemRequests) {
        return orderLineItemRequests.stream()
                .map(OrderLineItem::getMenuId)
                .toList();
    }
}
