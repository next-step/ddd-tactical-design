package kitchenpos.deliveryorders.tobe.domain;

import javax.persistence.Embeddable;

@Embeddable
public class DeliveryOrderLineItemQuantity {

    private long quantity;

    protected DeliveryOrderLineItemQuantity() {

    }

    public DeliveryOrderLineItemQuantity(final long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryOrderLineItemQuantity that = (DeliveryOrderLineItemQuantity) o;

        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return (int) (quantity ^ (quantity >>> 32));
    }
}
