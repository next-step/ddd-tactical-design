package kitchenpos.eatinorders.tobe.domain.order;

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

    public void verify(final EatInOrderMenus eatInOrderMenus) {
        if(!eatInOrderMenus.isSameSize(eatInOrderLineItems.size())) {
            throw new IllegalArgumentException();
        }
        eatInOrderLineItems.forEach(eatInOrderLineItem -> verify(eatInOrderMenus, eatInOrderLineItem));
    }

    private void verify(final EatInOrderMenus eatInOrderMenus, final EatInOrderLineItem eatInOrderLineItem) {
        if(!eatInOrderMenus.isDisplayed(eatInOrderLineItem.menuId())) {
            throw new IllegalArgumentException();
        }
        if(!eatInOrderMenus.isSamePrice(eatInOrderLineItem.menuId(), eatInOrderLineItem.menuPrice())) {
            throw new IllegalArgumentException();
        }
    }
}
