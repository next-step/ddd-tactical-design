package kitchenpos.eatinorders.tobe.domain;

import java.util.List;
import java.util.UUID;

public class EatInOrderLineItems {
    private final List<EatInOrderLineItem> eatInOrderLineItems;

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
}
