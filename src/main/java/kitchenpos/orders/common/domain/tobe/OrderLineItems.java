package kitchenpos.orders.common.domain.tobe;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

@Embeddable
public class OrderLineItems implements Iterable<OrderLineItem> {

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
        this.orderLineItems = orderLineItems;
    }

    @NotNull
    @Override
    public Iterator<OrderLineItem> iterator() {
        return orderLineItems.iterator();
    }
}
