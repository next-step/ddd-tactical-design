package kitchenpos.eatinorders.tobe.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;

@Table(name = "orders")
@Entity
public class EatInOrder {

    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @GeneratedValue
    private UUID id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private final OrderType type = OrderType.EAT_IN;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @ManyToOne
    @JoinColumn(
        name = "order_table_id",
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    protected EatInOrder() { }

    public EatInOrder(OrderLineItems orderLineItems, OrderTable orderTable) {
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
    }

    public void accept() {
        if (OrderStatus.WAITING != status) {
            throw new IllegalStateException("대기 상태의 주문만 접수할 수 있습니다.");
        }

        this.status = OrderStatus.ACCEPTED;
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

    public OrderTable getOrderTable() {
        return orderTable;
    }

    @PrePersist
    private void onPersist() {
        orderDateTime = LocalDateTime.now();
    }
}
