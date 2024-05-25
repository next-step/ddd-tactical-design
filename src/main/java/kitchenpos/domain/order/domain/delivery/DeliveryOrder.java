package kitchenpos.domain.order.domain.delivery;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import kitchenpos.domain.order.domain.Order;

@Entity
public class DeliveryOrder extends Order {

    @Column(name = "delivery_address")
    private String deliveryAddress;

    public DeliveryOrder() {
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(final String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
