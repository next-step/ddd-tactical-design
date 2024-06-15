package kitchenpos.order.tobe.eatinorder.domain;

import jakarta.persistence.*;
import kitchenpos.order.tobe.eatinorder.domain.validate.MenuValidator;

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

    protected OrderLineItems() {
    }

    public OrderLineItems(List<OrderLineItem> orderLineItems, MenuValidator menuValidator) {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문 항목이 비어 있습니다.");
        }
        menuValidator.validateOrderLineItems(orderLineItems);
        this.orderLineItems = orderLineItems;
    }

}
