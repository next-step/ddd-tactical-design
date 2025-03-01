package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.order.menu.EatInOrderMenus;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EatInOrderLineItems {
    private final List<EatInOrderLineItem> eatInOrderLineItems;

    public EatInOrderLineItems(final EatInOrderLineItem... eatInOrderLineItem) {
        this(List.of(eatInOrderLineItem));
    }

    public EatInOrderLineItems(final List<EatInOrderLineItem> eatInOrderLineItems) {
        if (eatInOrderLineItems == null || eatInOrderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.eatInOrderLineItems = eatInOrderLineItems;
    }

    /**
     * 무결성을 보장한다는 의미
      * @param eatInOrderMenus
     */
    public void ensureIntegrity(final EatInOrderMenus eatInOrderMenus) {
        if(!eatInOrderMenus.isSameSize(eatInOrderLineItems.size())) {
            throw new IllegalArgumentException();
        }
        eatInOrderLineItems.forEach(eatInOrderLineItem -> ensureIntegrity(eatInOrderMenus, eatInOrderLineItem));
    }

    private void ensureIntegrity(final EatInOrderMenus eatInOrderMenus, final EatInOrderLineItem eatInOrderLineItem) {
        if(!eatInOrderMenus.isDisplayed(eatInOrderLineItem.menuId())) {
            throw new IllegalArgumentException();
        }
        if(!eatInOrderMenus.isSamePrice(eatInOrderLineItem.menuId(), eatInOrderLineItem.orderLineItemPrice())) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isSamePrice(final UUID menuId, final int menuPrice) {
        return eatInOrderLineItems.stream()
                .filter(eatInOrderLineItem -> eatInOrderLineItem.isSameMenu(menuId))
                .findFirst()
                .map(eatInOrderLineItem -> eatInOrderLineItem.isSamePrice(menuPrice))
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<UUID> menuIds() {
        return eatInOrderLineItems.stream()
                .map(EatInOrderLineItem::menuId)
                .toList();
    }

    public int size() {
        return eatInOrderLineItems.size();
    }

    public List<EatInOrderLineItem> eatInOrderLineItems() {
        return new ArrayList<>(eatInOrderLineItems);
    }

    public void setEatInOrderMenuId(final EatInOrderId eatInOrderId) {
        eatInOrderLineItems.forEach(eatInOrderLineItem -> eatInOrderLineItem.setEatInOrderId(eatInOrderId));
    }
}
