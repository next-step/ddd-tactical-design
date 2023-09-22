package kitchenpos.order.domain;

import kitchenpos.order.domain.OrderLineItem;
import kitchenpos.support.ValueObject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Embeddable
public class OrderLineItems {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
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
