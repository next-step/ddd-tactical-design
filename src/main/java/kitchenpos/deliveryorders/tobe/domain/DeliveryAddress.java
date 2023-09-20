package kitchenpos.deliveryorders.tobe.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DeliveryAddress {

    private String address;

    protected DeliveryAddress() {
    }

    public DeliveryAddress(final String address) {
        if (Objects.isNull(address) || address.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryAddress that = (DeliveryAddress) o;

        return address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
