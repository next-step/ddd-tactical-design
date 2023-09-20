package kitchenpos.eatinorders.domain.vo;

import kitchenpos.eatinorders.domain.OrderLineItem;
import kitchenpos.support.ValueObject;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Embeddable
public class OrderLineItems extends ValueObject {
    private List<OrderLineItem> orderLineItems;

    public OrderLineItems() {
    }

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.orderLineItems = orderLineItems;
    }

    public BigDecimal sum() {
        return this.orderLineItems.stream()
                .map(OrderLineItem::multiply)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }
}
