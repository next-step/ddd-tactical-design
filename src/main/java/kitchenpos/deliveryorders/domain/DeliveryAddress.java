package kitchenpos.deliveryorders.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class DeliveryAddress {
  @Column(name = "delivery_address")
  private String deliveryAddress;

  public DeliveryAddress(final String deliveryAddress) {
    if (Objects.isNull(deliveryAddress) || deliveryAddress.isBlank()) {
      throw new IllegalArgumentException();
    }

    this.deliveryAddress = deliveryAddress;
  }

  public DeliveryAddress() {}

  public String getDeliveryAddress() {
    return deliveryAddress;
  }
}
