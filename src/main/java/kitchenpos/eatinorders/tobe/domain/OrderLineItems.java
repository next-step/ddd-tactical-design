package kitchenpos.eatinorders.tobe.domain;

import java.util.ArrayList;
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
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> values = new ArrayList<>();

    protected OrderLineItems() {
    }

    public OrderLineItems(OrderLineItem... values) {
        this(List.of(values));
    }

    public OrderLineItems(List<OrderLineItem> values) {
        validate(values);
        this.values = values;
    }

    private void validate(List<OrderLineItem> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("주문항목 목록은 하나 이상 있어야 합니다.");
        }
    }
}
