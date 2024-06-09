package kitchenpos.deliveryorders.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;
import kitchenpos.support.domain.OrderLineItem;
import kitchenpos.support.domain.OrderLineItems;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.INVALID_ORDER_STATUS;

@Table(name = "delivery_orders")
@Entity
public class DeliveryOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private DeliveryOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    @JoinColumn(
            name = "delivery_order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_delivery_order_line_item_to_orders")
    )
    private OrderLineItems orderLineItems;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    protected DeliveryOrder() {
    }

    protected DeliveryOrder(UUID id, DeliveryOrderStatus status, LocalDateTime orderDateTime, OrderLineItems orderLineItems, String deliveryAddress) {
        orderLineItems.checkNegativeQuantity();
        if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
    }

    public static DeliveryOrder create(OrderLineItems orderLineItems, String deliveryAddress) {
        return new DeliveryOrder(UUID.randomUUID(), DeliveryOrderStatus.WAITING, LocalDateTime.now(), orderLineItems, deliveryAddress);
    }

    public static DeliveryOrder create(OrderLineItems orderLineItems, String deliveryAddress, DeliveryOrderStatus status) {
        return new DeliveryOrder(UUID.randomUUID(), status, LocalDateTime.now(), orderLineItems, deliveryAddress);
    }

    public void accept() {
        changeFrom(DeliveryOrderStatus.WAITING);
    }

    public void serve() {
        changeFrom(DeliveryOrderStatus.ACCEPTED);
    }

    public void startDelivery() {
        changeFrom(DeliveryOrderStatus.SERVED);
    }

    public void completeDelivery() {
        changeFrom(DeliveryOrderStatus.DELIVERING);
    }

    public void complete() {
        changeFrom(DeliveryOrderStatus.DELIVERED);
    }

    public BigDecimal totalAmounts() {
        return orderLineItems.sumAmounts();
    }

    public UUID getId() {
        return id;
    }

    public DeliveryOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems.getValues();
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    private void changeFrom(DeliveryOrderStatus previousStatus) {
        if (this.status != previousStatus) {
            throw new KitchenPosIllegalStateException(INVALID_ORDER_STATUS, previousStatus);
        }
        this.status = previousStatus.next();
    }
}
