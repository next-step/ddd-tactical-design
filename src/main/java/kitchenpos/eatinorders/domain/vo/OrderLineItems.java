package kitchenpos.eatinorders.domain.vo;

import kitchenpos.eatinorders.domain.OrderLineItem;
import kitchenpos.menus.domain.Menu;
import kitchenpos.support.ValueObject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Embeddable
public class OrderLineItems extends ValueObject {
    private List<OrderLineItem> orderLineItems;

    public OrderLineItems() {
    }

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
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
