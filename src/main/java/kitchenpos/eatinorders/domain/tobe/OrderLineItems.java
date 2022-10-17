package kitchenpos.eatinorders.domain.tobe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderLineItems {
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    public OrderLineItems(final List<OrderLineItem> orderLineItems) {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("orderLineItems can not empty");
        }
        this.orderLineItems = orderLineItems;
    }

    public BigDecimal totalPrice() {
        return orderLineItems.stream()
            .map(OrderLineItem::totalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderLineItems that = (OrderLineItems) o;

        return Objects.equals(orderLineItems, that.orderLineItems);
    }

    @Override
    public int hashCode() {
        return orderLineItems != null ? orderLineItems.hashCode() : 0;
    }
}
