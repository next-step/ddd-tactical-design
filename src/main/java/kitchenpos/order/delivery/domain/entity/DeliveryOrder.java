package kitchenpos.order.delivery.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import kitchenpos.order.common.domain.entity.Order;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.entity.OrderType;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItems;
import kitchenpos.order.common.domain.model.OrderVo.Create;
import kitchenpos.order.delivery.domain.model.DeliveryInfo;

@Entity
@DiscriminatorValue("DELIVERY")
public class DeliveryOrder extends Order {

    @Column(name = "delivery_address")
    private String deliveryAddress;

    protected DeliveryOrder() {}

    public DeliveryOrder(OrderId orderId, OrderType orderType, OrderStatus orderStatus, OrderLineItems orderLineItems, DeliveryInfo deliveryInfo) {
        super(orderId, orderType, orderStatus, LocalDateTime.now(), orderLineItems, null);
        this.deliveryAddress = deliveryInfo.address();
    }

    public DeliveryOrder(OrderId orderId, OrderLineItems orderLineItems, DeliveryInfo deliveryInfo) {
        super(orderId, OrderType.DELIVERY, OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, null);
        this.deliveryAddress = deliveryInfo.address();
    }

    public static DeliveryOrder createDeliveryOrder(OrderId orderId, Create request, OrderLineItems orderLineItems) {
        final var deliveryAddress = DeliveryInfo.of(request.deliveryAddress());

        return new DeliveryOrder(orderId, orderLineItems, deliveryAddress);
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

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
