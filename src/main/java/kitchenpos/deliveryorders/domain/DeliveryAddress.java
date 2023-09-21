package kitchenpos.deliveryorders.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class DeliveryAddress {
    @Column(name = "delivery_address")
    private String fullAddress;

    @Transient
    private String address;

    @Transient
    private String addressDetail;

    @Transient
    private String zipCode;

    protected DeliveryAddress() {
    }

    private DeliveryAddress(String address, String addressDetail, String zipCode) {
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
        this.fullAddress = String.format("%s %s, %s", address, addressDetail, zipCode);
    }

    public static DeliveryAddress of(String address) {
        if (Objects.isNull(address) || address.isEmpty()) {
            throw new IllegalArgumentException("배달 주소는 비워 둘 수 없습니다.");
        }
        return new DeliveryAddress(address, "", "");
    }

    public static DeliveryAddress of(String address, String addressDetail) {
        return new DeliveryAddress(address, addressDetail, "");
    }

    public static DeliveryAddress of(String address, String addressDetail, String zipCode) {
        return new DeliveryAddress(address, addressDetail, zipCode);
    }

    public String getValue() {
        return fullAddress;
    }
}
