package kitchenpos.takeoutorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.sharedkernel.OrderLineItems;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Embeddable
public class TakeOutOrderLineItems extends OrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<TakeOutOrderLineItem> takeOutOrderLineItemList;

    public TakeOutOrderLineItems(final List<TakeOutOrderLineItem> takeOutOrderLineItemList, final Map<UUID, Menu> menuMap) {
        takeOutOrderLineItemList.forEach(orderLineItem -> {
            Menu menu = menuMap.get(orderLineItem.getMenuId());
            validateMenu(menu);
            validateMenuPrice(orderLineItem, menu);
        });
        this.takeOutOrderLineItemList = takeOutOrderLineItemList;
    }

    protected TakeOutOrderLineItems() {

    }
}
