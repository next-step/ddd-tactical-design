package kitchenpos.deliveryorders.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DeliveryOrderAddress {
    @Column(name = "address", nullable = false)
    private String deliveryOrderAddress;

    protected DeliveryOrderAddress() {
    }

    public DeliveryOrderAddress(String deliveryOrderAddress) {
        validateDeliveryOrderAddress(deliveryOrderAddress);
        this.deliveryOrderAddress = deliveryOrderAddress;
    }

    private void validateDeliveryOrderAddress(String deliveryOrderAddress) {
        if (Objects.isNull(deliveryOrderAddress) || deliveryOrderAddress.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public String getDeliveryOrderAddress() {
        return deliveryOrderAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryOrderAddress that = (DeliveryOrderAddress) o;
        return Objects.equals(deliveryOrderAddress, that.deliveryOrderAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryOrderAddress);
    }
}
