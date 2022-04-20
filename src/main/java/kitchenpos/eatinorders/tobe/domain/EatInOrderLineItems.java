package kitchenpos.eatinorders.tobe.domain;

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
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<EatInOrderLineItem> eatInOrderLineItems;

    protected EatInOrderLineItems() {}

    private EatInOrderLineItems(final List<EatInOrderLineItem> eatInOrderLineItems) {
        this.eatInOrderLineItems = eatInOrderLineItems;
    }

    public static EatInOrderLineItems of(final List<EatInOrderLineItem> eatInOrderLineItems) {
        return new EatInOrderLineItems(eatInOrderLineItems);
    }
}
