package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Embeddable
public class OrderLineItems {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<EatInOrderLineItem> orderLineItems;


    protected OrderLineItems() {
    }

    private OrderLineItems(List<EatInOrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public static OrderLineItems of(List<EatInOrderLineItem> orderLineItems) {
        return new OrderLineItems(orderLineItems);
    }

    public Integer size() {
        return orderLineItems.size();
    }
}
