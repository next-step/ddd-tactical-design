package kitchenpos.deliveryorders.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DeliveryOrderQuantity {
    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected DeliveryOrderQuantity() {
    }

    private DeliveryOrderQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량은 0보다 작을 수 없습니다.");
        }
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DeliveryOrderQuantity quantity1 = (DeliveryOrderQuantity)o;
        return quantity == quantity1.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }

    public static DeliveryOrderQuantity of(long quantity) {
        return new DeliveryOrderQuantity(quantity);
    }

    public long value() {
        return quantity;
    }
}
