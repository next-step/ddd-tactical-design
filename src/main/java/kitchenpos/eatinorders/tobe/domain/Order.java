package kitchenpos.eatinorders.tobe.domain;

import java.time.LocalDateTime;
import java.util.UUID;
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

@Table(name = "orders")
@Entity
public class Order {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    public OrderLineItems orderLineItems;

    @ManyToOne
    @JoinColumn(
        name = "order_table_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    protected Order() {
    }

    public Order(OrderLineItems orderLineItems, OrderTable orderTable) {
        this(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, orderTable);
    }

    protected Order(UUID id, OrderStatus status, LocalDateTime orderDateTime,
        OrderLineItems orderLineItems, OrderTable orderTable) {
        validateOrderLineItems(orderLineItems);
        validateOrderTable(orderTable);
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
    }

    private void validateOrderLineItems(OrderLineItems orderLineItems) {
        if (orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문항목 목록은 하나 이상 있어야 합니다.");
        }
        if (orderLineItems.isAnyNotDisplayed()) {
            throw new IllegalStateException("숨겨져 있는 주문항목이 있습니다.");
        }
    }

    private void validateOrderTable(OrderTable orderTable) {
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException("빈 테이블에는 매장 주문을 등록할 수 없습니다.");
        }
    }

    public void accept() {
        if (this.status != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (this.status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        this.status = OrderStatus.SERVED;
    }

    public void complete() {
        if (this.status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        this.status = OrderStatus.COMPLETED;
        this.orderTable.clear();
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

    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }
}
