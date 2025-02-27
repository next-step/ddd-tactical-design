package kitchenpos.order.deliveryorder.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kitchenpos.order.common.model.Order;
import kitchenpos.order.common.model.OrderType;

@Entity
@DiscriminatorValue("DELIVERY")
public class DeliveryOrder extends Order {

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Override
    public OrderType getType() {
        return OrderType.DELIVERY;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(final String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
