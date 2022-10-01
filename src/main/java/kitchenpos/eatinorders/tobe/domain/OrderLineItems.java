package kitchenpos.eatinorders.tobe.domain;

import static kitchenpos.global.utils.CollectionUtils.isEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class OrderLineItems implements Serializable {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> values = new ArrayList<>();

    protected OrderLineItems() {
    }

    public OrderLineItems(List<OrderLineItem> values) {
        validate(values);
        this.values = values;
    }

    private void validate(List<OrderLineItem> values) {
        if (isEmpty(values)) {
            throw new IllegalArgumentException();
        }
    }

    public List<OrderLineItem> getValues() {
        return Collections.unmodifiableList(values);
    }
}
