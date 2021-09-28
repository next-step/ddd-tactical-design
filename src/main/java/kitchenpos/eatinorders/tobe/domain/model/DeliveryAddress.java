package kitchenpos.eatinorders.tobe.domain.model;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class DeliveryAddress {

    private String address;

    public DeliveryAddress() {

    }

    public DeliveryAddress(String address) {
        validateAddress(address);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    private void validateAddress(String address) {
        if (Objects.isNull(address) || address.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
