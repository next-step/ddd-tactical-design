package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DeliveryAddress {
    @Column(name = "delivery_address")
    private String value;

    protected DeliveryAddress() {
    }

    public DeliveryAddress(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public String getValue() {
        return value;
    }
}
