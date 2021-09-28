package kitchenpos.eatinorders.tobe.domain.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import kitchenpos.common.domain.MenuId;
import kitchenpos.common.domain.Price;

@Embeddable
public class OrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> orderLineItems;

    protected OrderLineItems() {
    }

    public OrderLineItems(final List<OrderLineItem> orderLineItems) {
        this.orderLineItems = new ArrayList<>(orderLineItems);
    }

    public boolean isPriceValid(final MenuId menuId, final Price menuPrice) {
        final OrderLineItem targetOrderLineItem = this.orderLineItems
            .stream()
            .filter(item -> item.getMenuId().equals(menuId))
            .findFirst()
            .orElseThrow(NoSuchElementException::new);
        return targetOrderLineItem.calculateTotalPrice().equals(menuPrice);
    }

    public List<MenuId> getMenuIds() {
        final List<MenuId> menuIds = this.orderLineItems
            .stream()
            .map(OrderLineItem::getMenuId)
            .collect(Collectors.toList());
        return Collections.unmodifiableList(menuIds);
    }

    public Price calculateTotalPrice() {
        return this.orderLineItems
            .stream()
            .map(OrderLineItem::calculateTotalPrice)
            .reduce(Price.ZERO, Price::add);
    }
}
