package kitchenpos.eatinorders.tobe.domain;

import java.util.ArrayList;
import java.util.Collections;
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
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> items;

    protected OrderLineItems() { }

    public OrderLineItems(final List<OrderLineItem> items) {
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
    }

    public long sumOfMenuPrices() {
        return items.stream()
                    .mapToLong(OrderLineItem::getOrderLinePrice)
                    .sum();
    }

    public List<OrderLineItem> getItems() {
        return items;
    }
}
