package kitchenpos.eatinorders.tobe.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import kitchenpos.eatinorders.tobe.domain.interfaces.KitchenridersClient;

@Entity
@DiscriminatorValue("Delivery")
public class DeliveryOrder extends Order {

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryOrderStatus status;

    @Column(name = "delivery_address")
    private DeliveryAddress deliveryAddress;

    public DeliveryOrder() {

    }

    public DeliveryOrder(List<OrderLineItem> orderLineItems, String deliveryAddress) {
        super(OrderType.DELIVERY, LocalDateTime.now(), orderLineItems);
        this.status = DeliveryOrderStatus.WAITING;
        this.deliveryAddress = new DeliveryAddress(deliveryAddress);
    }

    public void accept(KitchenridersClient kitchenridersClient) {
        if (this.status != DeliveryOrderStatus.WAITING) {
            throw new IllegalStateException();
        }

        this.status = DeliveryOrderStatus.ACCEPTED;
        kitchenridersClient.requestDelivery(this.getId(), getTotalAmount(), deliveryAddress.getAddress());
    }

    public void serve() {
        if (this.status != DeliveryOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }

        this.status = DeliveryOrderStatus.SERVED;
    }

    public void startDelivery() {
        if (this.status != DeliveryOrderStatus.SERVED) {
            throw new IllegalStateException();
        }

        this.status = DeliveryOrderStatus.DELIVERING;
    }

    public void completeDelivery() {
        if (this.status != DeliveryOrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }

        this.status = DeliveryOrderStatus.DELIVERED;
    }

    public void complete() {
        if (this.status != DeliveryOrderStatus.DELIVERED) {
            throw new IllegalStateException();
        }

        this.status = DeliveryOrderStatus.COMPLETED;
    }

    public DeliveryOrderStatus getStatus() {
        return status;
    }

    public String getDeliveryAddress() {
        return deliveryAddress.getAddress();
    }

    private BigDecimal getTotalAmount() {
        BigDecimal sum = BigDecimal.ZERO;
        for (final OrderLineItem orderLineItem : getOrderLineItems()) {
            sum = orderLineItem.getMenuPrice()
                    .multiply(BigDecimal.valueOf(orderLineItem.getQuantity()));
        }

        return sum;
    }
}
