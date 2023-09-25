package kitchenpos.eatinorders.domain;

import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static kitchenpos.eatinorders.exception.OrderExceptionMessage.ORDER_LINE_ITEM_EMPTY;

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

    public EatInOrderLineItems() {
    }

    public EatInOrderLineItems(List<EatInOrderLineItem> orderLineItems) {
        if (ObjectUtils.isEmpty(orderLineItems)) {
            throw new IllegalArgumentException(ORDER_LINE_ITEM_EMPTY);
        }
        this.orderLineItems = orderLineItems;
    }

    public static EatInOrderLineItems create(List<EatInOrderLineItem> eatInOrderLineItems) {
        return new EatInOrderLineItems(eatInOrderLineItems);
    }

    public List<EatInOrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderLineItems that = (EatInOrderLineItems) o;
        return Objects.equals(orderLineItems, that.orderLineItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderLineItems);
    }
}
