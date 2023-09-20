package kitchenpos.deliveryorders.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class DeliveryOrderLineItems {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "delivery_order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_delivery_order_line_item_to_orders")
    )
    private List<DeliveryOrderLineItem> values;

    protected DeliveryOrderLineItems() {
    }

    public DeliveryOrderLineItems(List<DeliveryOrderLineItem> orderLineItems) {
        if (orderLineItems == null || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문 내역이 없으면 등록할 수 없다.");
        }
        this.values = orderLineItems;
    }

    public BigDecimal sumOfOrderPrice() {
        return values.stream()
            .map(DeliveryOrderLineItem::menuPrice)
            .reduce(DeliveryOrderMenuPrice.ZERO, DeliveryOrderMenuPrice::add)
            .getValue();
    }
}
