package kitchenpos.deliveryorders.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Embeddable
public class DeliveryOrderLineItems {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<DeliveryOrderLineItem> orderLineItems;

    protected DeliveryOrderLineItems() {
    }

    public DeliveryOrderLineItems(List<DeliveryOrderLineItem> orderLineItems) {
        validateDeliveryOrderLineItems(orderLineItems);
        this.orderLineItems = orderLineItems;
    }

    private void validateDeliveryOrderLineItems(List<DeliveryOrderLineItem> orderLineItems) {
        if (orderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public List<DeliveryOrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public BigDecimal getTotalDeliveryOrderLineItemsPrice() {
        return orderLineItems.stream().map(DeliveryOrderLineItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
