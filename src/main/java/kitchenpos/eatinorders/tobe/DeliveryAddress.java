package kitchenpos.eatinorders.tobe;

import javax.persistence.Embeddable;

@Embeddable
public class DeliveryAddress {

    private final String address;

    public DeliveryAddress(String address) {
        this.address = address;
    }

    public static DeliveryAddress empty() {
        return new DeliveryAddress("");
    }
}
