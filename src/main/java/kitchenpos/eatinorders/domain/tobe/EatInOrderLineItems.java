package kitchenpos.eatinorders.domain.tobe;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        validateValues(orderLineItems);
        this.orderLineItems = orderLineItems;
    }

    private void validateValues(List<EatInOrderLineItem> orderLineItems) {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public List<EatInOrderLineItem> getEatInOrderLineItem() {
        return Collections.unmodifiableList(orderLineItems);
    }
}
