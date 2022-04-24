package kitchenpos.eatinorders.domain.tobe.domain;

import javax.persistence.*;
import java.util.List;

@Embeddable
public class OrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<TobeOrderLineItem> orderLineItems;

    public OrderLineItems(List<TobeOrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    protected OrderLineItems() {

    }

    public boolean isEmpty() {
        return orderLineItems.isEmpty();
    }

    public List<TobeOrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }
}
