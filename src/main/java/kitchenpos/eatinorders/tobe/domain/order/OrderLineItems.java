package kitchenpos.eatinorders.tobe.domain.order;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class OrderLineItems {

    private final List<OrderLineItem> elements;

    public OrderLineItems(List<OrderLineItem> elements) {
        if (Objects.isNull(elements) || elements.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.elements = elements;
    }

    public OrderLineItems(OrderLineItem... elements) {
        this(Arrays.asList(elements));
    }
}
