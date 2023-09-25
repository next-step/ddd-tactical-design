package kitchenpos.eatinorders.tobe.domain;

import org.thymeleaf.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DeliveryAddress {
    @Column(name = "delivery_address")
    private String deliveryAddress;

    protected DeliveryAddress() {

    }


    private DeliveryAddress(String deliveryAddress) {

        this.deliveryAddress = deliveryAddress;
    }

    public static DeliveryAddress create(String deliveryAddress) {
        return new DeliveryAddress(deliveryAddress);
    }

}
