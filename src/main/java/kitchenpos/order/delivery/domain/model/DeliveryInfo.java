package kitchenpos.order.delivery.domain.model;

import jakarta.persistence.Embeddable;
import kitchenpos.order.delivery.exception.DeliveryInfoException;

@Embeddable
public record DeliveryInfo(String address) {

    public static DeliveryInfo of(String address) {
        if (address == null || address.isEmpty()) {
            throw new DeliveryInfoException();
        }
        return new DeliveryInfo(address);
    }

    public String get() {
        return address;
    }
}
