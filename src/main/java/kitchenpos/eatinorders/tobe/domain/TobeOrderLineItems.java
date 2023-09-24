package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Embeddable
public class TobeOrderLineItems {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<TobeOrderLineItem> orderLineItems;

    protected TobeOrderLineItems() {
    }

    public TobeOrderLineItems(final List<TobeOrderLineItem> orderLineItems) {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.orderLineItems = orderLineItems;
    }

    public List<TobeOrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public List<UUID> getMenuIds() {
        return orderLineItems.stream().map(TobeOrderLineItem::getMenuId).collect(Collectors.toList());
    }

    public void validation(final int size) {
        if (orderLineItems.size() != size) {
            throw new IllegalArgumentException();
        }
    }
}
