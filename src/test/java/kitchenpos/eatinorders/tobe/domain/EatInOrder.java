package kitchenpos.eatinorders.tobe.domain;

import java.util.List;

public class EatInOrder {

    public EatInOrder(final List<EatInOrderLineItem> eatInOrderLineItems) {
        if (eatInOrderLineItems == null || eatInOrderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
