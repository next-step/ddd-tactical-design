package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class OrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "eat_in_orders_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> orderLineItems;

    protected OrderLineItems() { }

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public List<Long> getOrderLineItemSequence() {
        return orderLineItems.stream()
                .map(OrderLineItem::getSeq)
                .collect(Collectors.toList());
    }
}
