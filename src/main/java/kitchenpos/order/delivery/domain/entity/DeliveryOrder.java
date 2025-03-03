package kitchenpos.order.delivery.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kitchenpos.order.common.domain.entity.Order;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.entity.OrderType;

@Entity
@DiscriminatorValue("DELIVERY")
public class DeliveryOrder extends Order {

    @Column(name = "delivery_address")
    private String deliveryAddress;

    protected DeliveryOrder() {}

    public boolean isDelivery() {
        return this.getType() == OrderType.DELIVERY;
    }

    public boolean isServed() {
        return this.getStatus() == OrderStatus.SERVED;
    }

    public boolean isDelivering() {
        return this.getStatus() == OrderStatus.DELIVERING;
    }

    public void validateStartDeliveryOrder() {
        if (!isDelivery()) {
            throw new IllegalStateException("배달 주문이 아닙니다.");
        }
        if (!isServed()) {
            throw new IllegalStateException("주문이 준비되지 않았습니다.");
        }
    }

    public void validateCompleteDeliveryOrder() {
        if (!isDelivering()) {
            throw new IllegalStateException("배달이 시작되지 않았습니다.");
        }
    }
}
