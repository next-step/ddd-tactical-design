package kitchenpos.eatinorders.tobe.domain.order;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
public class EatInOrderLineItems {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<EatInOrderLineItem> orderLineItems;

    protected EatInOrderLineItems() {
    }

    public EatInOrderLineItems(List<EatInOrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public List<EatInOrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }
}
