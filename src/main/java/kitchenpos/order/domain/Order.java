package kitchenpos.order.domain;

import kitchenpos.order.deliveryorders.infra.KitchenridersClient;
import kitchenpos.order.eatinorders.domain.OrderTableClearService;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class Order {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @ManyToOne
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    @Transient
    private UUID orderTableId;

    public Order() {
    }

    public Order(UUID id, OrderType type, OrderStatus status, LocalDateTime orderDateTime, OrderLineItems orderLineItems, String deliveryAddress, OrderTable orderTable, UUID orderTableId) {
        validateOrderLineItems(type, orderLineItems);
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.orderTable = orderTable;
        this.orderTableId = orderTableId;
    }

    public void chageStatus(final OrderStatus status) {
        this.status = status;
    }

    public void validateOrderLineItems(OrderType type, OrderLineItems orderLineItems) {
        if (type != OrderType.EAT_IN) {
            orderLineItems.getOrderLineItems().stream()
                    .map(OrderLineItem::getQuantity)
                    .forEach(quantity -> {
                        if (quantity < 0) {
                            throw new IllegalArgumentException();
                        }
                    });
        }
    }

    public Order accept(KitchenridersClient kitchenridersClient) {
        if (getStatus() != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        BigDecimal sum = getOrderLineItems().sum();
        if (getType() == OrderType.DELIVERY) {
            kitchenridersClient.requestDelivery(getId(), sum, getDeliveryAddress());
        }
        chageStatus(OrderStatus.ACCEPTED);
        return this;
    }

    public Order serve() {
        if (getStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        chageStatus(OrderStatus.SERVED);
        return this;
    }

    public Order complete(OrderTableClearService orderTableClearService) {
        if (getType() == OrderType.DELIVERY) {
            if (getStatus() != OrderStatus.DELIVERED) {
                throw new IllegalStateException();
            }
        }
        if (getType() == OrderType.EAT_IN || getType() == OrderType.TAKEOUT) {
            if (getStatus() != OrderStatus.SERVED) {
                throw new IllegalStateException();
            }
        }
        chageStatus(OrderStatus.COMPLETED);
        if (getType() == OrderType.EAT_IN) {
            orderTableClearService.clear(this);
        }
        return this;
    }

    public Order startDelivery() {
        if (getType() != OrderType.DELIVERY) {
            throw new IllegalStateException();
        }
        if (getStatus() != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        chageStatus(OrderStatus.DELIVERING);
        return this;
    }

    public Order completeDelivery() {
        if (getType() != OrderType.DELIVERY) {
            throw new IllegalStateException();
        }
        if (getStatus() != OrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }
        chageStatus(OrderStatus.DELIVERED);
        return this;
    }


    public boolean isCompleted() {
        return this.status == OrderStatus.COMPLETED;
    }

    public UUID getId() {
        return id;
    }

    public OrderType getType() {
        return type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
