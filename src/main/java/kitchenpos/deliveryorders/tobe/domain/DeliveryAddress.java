package kitchenpos.deliveryorders.tobe.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DeliveryAddress {

    private String deliveryAddress;

    protected DeliveryAddress() {
    }

    public DeliveryAddress(final String deliveryAddress) {
        if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryAddress that = (DeliveryAddress) o;

        return deliveryAddress.equals(that.deliveryAddress);
    }

    @Override
    public int hashCode() {
        return deliveryAddress.hashCode();
    }
}
