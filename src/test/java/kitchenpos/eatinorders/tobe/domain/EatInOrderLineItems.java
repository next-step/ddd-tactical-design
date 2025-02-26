package kitchenpos.eatinorders.tobe.domain;

import java.util.List;

public class EatInOrderLineItems {
    private final List<EatInOrderLineItem> eatInOrderLineItems;

    public EatInOrderLineItems(final List<EatInOrderLineItem> eatInOrderLineItems) {
        if (eatInOrderLineItems == null || eatInOrderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.eatInOrderLineItems = eatInOrderLineItems;
    }
}
