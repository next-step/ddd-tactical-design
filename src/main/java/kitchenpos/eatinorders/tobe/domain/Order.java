package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    private OrderLineItems orderLineItems = new OrderLineItems();

    @Embedded
    private DeliveryAddress deliveryAddress;

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

    public Order(OrderType type, OrderStatus status, LocalDateTime orderDateTime, List<OrderLineItem> orderLineItems, OrderTable orderTable) {
        validateOrderType(type);
        this.id = UUID.randomUUID();
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems.addAll(orderLineItems);
        this.orderTable = orderTable;
    }

    private static void validateOrderType(OrderType type) {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException();
        }
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

    public void accept() {
        if (this.status != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void served() {
        if (this.status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        this.status = OrderStatus.SERVED;
    }

    public void completed() {
        if (this.status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        this.status = OrderStatus.COMPLETED;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems.get();
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public UUID getOrderTableId() {
        return orderTable.getId();
    }
}
