package kitchenpos.eatinorders.tobe.domain;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DeliveryAddress {
    private static final String DELIVERY_ADDRESS_MUST_NOT_BE_EMPTY = "배달 주소는 빈 값이 아니어야 합니다. 입력 값 : %s";
    @Column(name = "delivery_address")
    private final String address;

    protected DeliveryAddress() {
        this.address = null;
    }

    public DeliveryAddress(String address) {
        validate(address);
        this.address = address;
    }

    private void validate(String address) {
        if (Strings.isEmpty(address)) {
            throw new IllegalArgumentException(String.format(DELIVERY_ADDRESS_MUST_NOT_BE_EMPTY, address));
        }
    }
}
