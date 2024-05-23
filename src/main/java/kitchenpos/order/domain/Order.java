package kitchenpos.order.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "orders")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "order_type")
public abstract class Order {
    public static final String INVALID_ORDER_STATUS_ERROR = "유효하지 않은 주문 상태입니다.";

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    protected OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @ManyToOne
    @JoinColumn(name = "order_table_id", columnDefinition = "binary(16)", foreignKey = @ForeignKey(name = "fk_orders_to_order_table"))
    private OrderTable orderTable;

    protected Order() {
    }

    protected Order(OrderType type) {
        this.type = type;
    }

    protected Order(
        OrderType type,
        OrderStatus status,
        OrderLineItems orderLineItems
    ) {
        this(UUID.randomUUID(), type, status, LocalDateTime.now(), orderLineItems, null);
    }

    protected Order(
        OrderType type,
        OrderStatus status,
        OrderLineItems orderLineItems,
        OrderTable orderTable
    ) {
        this(UUID.randomUUID(), type, status, LocalDateTime.now(), orderLineItems, orderTable);
    }

    protected Order(
        OrderType type,
        OrderStatus status,
        LocalDateTime orderDateTime,
        OrderLineItems orderLineItems,
        OrderTable orderTable
    ) {
        this(UUID.randomUUID(), type, status, orderDateTime, orderLineItems, orderTable);
    }

    protected Order(
        UUID id,
        OrderType type,
        OrderStatus status,
        LocalDateTime orderDateTime,
        OrderLineItems orderLineItems,
        OrderTable orderTable
    ) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
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

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems.getValue();
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public abstract Order accepted(KitchenridersClient kitchenridersClient);

    public abstract Order served();

    public abstract Order delivering();

    public abstract Order delivered();

    public abstract Order completed(OrderRepository orderRepository);
}
