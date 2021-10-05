package kitchenpos.eatinorders.tobe.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import kitchenpos.eatinorders.tobe.domain.handler.DomainExceptionHandler;
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

    public void accept(KitchenridersClient kitchenridersClient, DomainExceptionHandler domainExceptionHandler) {
        if (this.status != DeliveryOrderStatus.WAITING) {
            domainExceptionHandler.handle(ExceptionType.ILLEGAL_ORDER_STATUS);
        }

        this.status = DeliveryOrderStatus.ACCEPTED;
        kitchenridersClient.requestDelivery(this.getId(), getTotalAmount(), deliveryAddress.getAddress());
    }

    public void serve(DomainExceptionHandler domainExceptionHandler) {
        if (this.status != DeliveryOrderStatus.ACCEPTED) {
            domainExceptionHandler.handle(ExceptionType.ILLEGAL_ORDER_STATUS);
        }

        this.status = DeliveryOrderStatus.SERVED;
    }

    public void startDelivery(DomainExceptionHandler domainExceptionHandler) {
        if (this.status != DeliveryOrderStatus.SERVED) {
            domainExceptionHandler.handle(ExceptionType.ILLEGAL_ORDER_STATUS);
        }

        this.status = DeliveryOrderStatus.DELIVERING;
    }

    public void completeDelivery(DomainExceptionHandler domainExceptionHandler) {
        if (this.status != DeliveryOrderStatus.DELIVERING) {
            domainExceptionHandler.handle(ExceptionType.ILLEGAL_ORDER_STATUS);
        }

        this.status = DeliveryOrderStatus.DELIVERED;
    }

    public void complete(DomainExceptionHandler domainExceptionHandler) {
        if (this.status != DeliveryOrderStatus.DELIVERED) {
            domainExceptionHandler.handle(ExceptionType.ILLEGAL_ORDER_STATUS);
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
