package kitchenpos.eatinorders.tobe.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
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
        this.values = values;
    }

    public BigDecimal getTotalAmount() {
        return values.stream()
            .map(OrderLineItem::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isAnyNotDisplayed() {
        return values.stream()
            .anyMatch(Predicate.not(OrderLineItem::isDisplayed));
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }
}
