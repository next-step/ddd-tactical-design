package kitchenpos.eatinorders.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class EatInOrderLineItems {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "eat_in_order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_eat_in_order_line_item_to_orders")
    )
    private List<EatInOrderLineItem> values;

    protected EatInOrderLineItems() {
    }

    public EatInOrderLineItems(List<EatInOrderLineItem> orderLineItems) {
        if (orderLineItems == null || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문 내역이 없으면 등록할 수 없다.");
        }
        this.values = orderLineItems;
    }

    public BigDecimal sumOfOrderPrice() {
        return values.stream()
            .map(EatInOrderLineItem::menuPrice)
            .reduce(EatInOrderMenuPrice.ZERO, EatInOrderMenuPrice::add)
            .getValue();
    }
}
