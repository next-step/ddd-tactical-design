package kitchenpos.domain.order.tobe.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Embeddable
public class OrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "eat_in_order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_eat_in_order")
    )
    private List<OrderLineItem> orderLineItems;

    protected OrderLineItems() {
    }

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        if (orderLineItems == null || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문항목은 1개 이상이어야 합니다.");
        }
        this.orderLineItems = new ArrayList<>(orderLineItems);
    }

    public List<OrderLineItem> get() {
        return orderLineItems;
    }
}
