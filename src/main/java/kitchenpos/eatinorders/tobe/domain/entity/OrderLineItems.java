package kitchenpos.eatinorders.tobe.domain.entity;

import jakarta.persistence.*;
import kitchenpos.eatinorders.tobe.application.acl.EatInOrderServiceAdapter;

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
    private List<OrderLineItem> orderLineItems;

    protected OrderLineItems() {}

    public boolean hasDisplayedMenu(EatInOrderServiceAdapter orderDomainService) {
        return orderLineItems.stream()
                .map(orderLineItem -> orderLineItem.getMenuId())
                .anyMatch(menuId -> orderDomainService.existHideMenu(menuId));
    }

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }
}
