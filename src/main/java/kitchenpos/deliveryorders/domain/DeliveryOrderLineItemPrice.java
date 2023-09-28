package kitchenpos.deliveryorders.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class DeliveryOrderLineItemPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal deliveryOrderLineItemPrice;

    protected DeliveryOrderLineItemPrice() {
    }

    public DeliveryOrderLineItemPrice(BigDecimal deliveryOrderLineItemPrice) {
        this.deliveryOrderLineItemPrice = deliveryOrderLineItemPrice;
    }

    public BigDecimal getDeliveryOrderLineItemPrice() {
        return deliveryOrderLineItemPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryOrderLineItemPrice that = (DeliveryOrderLineItemPrice) o;
        return Objects.equals(deliveryOrderLineItemPrice, that.deliveryOrderLineItemPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryOrderLineItemPrice);
    }
}
