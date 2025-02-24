package kitchenpos.order.deliveryorder.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kitchenpos.order.common.domain.Order;
import kitchenpos.order.common.domain.OrderType;

@Entity
@DiscriminatorValue("DELIVERY")
public class DeliveryOrder extends Order {

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private DeliveryOrderStatus status;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(final String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public DeliveryOrder() {
    }

    @Override
    public OrderType getType() {
        return OrderType.DELIVERY;
    }

    public DeliveryOrderStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryOrderStatus status) {
        this.status = status;
    }
}
