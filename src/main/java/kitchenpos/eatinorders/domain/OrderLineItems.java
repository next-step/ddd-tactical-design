package kitchenpos.eatinorders.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

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

    protected OrderLineItems() {
    }

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        if (orderLineItems == null || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("주문 항목은 1개 이상이어야 합니다. 현재 값: %s",
                            orderLineItems == null ? null : orderLineItems.size())
            );
        }
        this.orderLineItems = orderLineItems;
    }
}
