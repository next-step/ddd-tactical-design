package kitchenpos.eatinorders.tobe.domain.model;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class OrderLineItems {

    public OrderLineItems(final List<OrderLineItem> OrderLineItems) {
    }

    public List<UUID> getMenuIds() {
        return Collections.emptyList();
    }

    public boolean canBeCompleted() {
        return false;
    }
}
