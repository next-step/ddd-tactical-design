package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.domain.vo.OrderLineItems;

import javax.persistence.*;
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

    public Order(UUID id, OrderType type, OrderStatus status, LocalDateTime orderDateTime, OrderLineItems orderLineItems, String deliveryAddress, OrderTable orderTable) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.orderTable = orderTable;
        this.orderTableId = orderTable.getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(final OrderType type) {
        this.type = type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(final OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(final LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(OrderLineItems orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(final String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public void setOrderTable(final OrderTable orderTable) {
        this.orderTable = orderTable;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void setOrderTableId(final UUID orderTableId) {
        this.orderTableId = orderTableId;
    }
}
