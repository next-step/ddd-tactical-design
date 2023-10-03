package kitchenpos.deliveryorders.domain;

import kitchenpos.eatinorders.domain.OrderStatus;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Table(name = "delivery_orders")
@Entity
public class DeliveryOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private DeliveryOrderLineItems orderLineItems;

    @Embedded
    private DeliveryOrderAddress deliveryAddress;

    protected DeliveryOrder() {
    }

    public DeliveryOrder(UUID id, OrderStatus status, LocalDateTime orderDateTime, DeliveryOrderLineItems orderLineItems, DeliveryOrderAddress deliveryAddress) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
    }

    public static DeliveryOrder of(DeliveryOrderLineItems orderLineItems, DeliveryOrderAddress orderAddress, MenuClient menuClient) {
        validateDeliveryOrder(orderLineItems, menuClient);
        return new DeliveryOrder(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, orderAddress);
    }

    private static void validateDeliveryOrder(DeliveryOrderLineItems orderLineItems, MenuClient menuClient) {
        int menusSize = menuClient.countAllByIdIn(
                orderLineItems.getOrderLineItems().stream()
                        .map(DeliveryOrderLineItem::getMenuId)
                        .collect(Collectors.toList())
        );
        if (menusSize != orderLineItems.getOrderLineItems().size()) {
            throw new IllegalArgumentException();
        }
    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<DeliveryOrderLineItem> getOrderLineItems() {
        return orderLineItems.getOrderLineItems();
    }

    public String  getDeliveryAddress() {
        return deliveryAddress.getDeliveryOrderAddress();
    }

    public void accept(KitchenridersClient kitchenridersClient) {
        if (status != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }

        kitchenridersClient.requestDelivery(id, orderLineItems.getTotalDeliveryOrderLineItemsPrice(), getDeliveryAddress());
        status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        this.status = OrderStatus.SERVED;
    }

    public void startDelivery() {
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.DELIVERING;
    }

    public void completeDelivery() {
        if (status != OrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }
        status = OrderStatus.DELIVERED;
    }

    public void complete() {
        if (status != OrderStatus.DELIVERED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.COMPLETED;
    }
}
