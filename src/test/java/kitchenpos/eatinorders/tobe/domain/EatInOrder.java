package kitchenpos.eatinorders.tobe.domain;

import java.util.List;

public class EatInOrder {
    private final EatInOrderLineItems eatInOrderLineItems;

    public EatInOrder(final List<EatInOrderLineItem> eatInOrderLineItems) {
        this.eatInOrderLineItems = new EatInOrderLineItems(eatInOrderLineItems);
    }
}
