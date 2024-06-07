package kitchenpos.eatinorders.tobe.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    protected OrderLineItems() {}

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public void updateMenuInOrders(UUID menuId, BigDecimal price, boolean displayed) {
        for (OrderLineItem orderLineItem : orderLineItems) {
            orderLineItem.updateMenuInOrders(menuId, price, displayed);
        }
    }

    public Set<UUID> allMenuId() {
        return orderLineItems.stream()
                .map(orderLineItem -> orderLineItem.getMenuId())
                .collect(Collectors.toSet());
    }
}
