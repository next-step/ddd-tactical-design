package kitchenpos.order.tobe.eatinorder.domain;

import jakarta.persistence.*;
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTable;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "eat_in_order")
@Entity
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    protected EatInOrder() {
    }

    public EatInOrder(UUID id, OrderLineItems orderLineItems, OrderTable orderTable) {
        this.id = id;
        this.status = EatInOrderStatus.WAITING;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = orderLineItems;
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException("주문 테이블이 비어있습니다.");
        }
        this.orderTable = orderTable;
    }

    public void accept() {
        if (this.status != EatInOrderStatus.WAITING) {
            throw new IllegalStateException("주문 접수중인 주문만 주문 수락 할 수 있습니다.");
        }
        status = EatInOrderStatus.ACCEPTED;
    }

    public void serve() {
        if (this.status != EatInOrderStatus.ACCEPTED) {
            throw new IllegalStateException("주문 수락된 주문만 준비완료로 변경할 수 있습니다.");
        }
        status = EatInOrderStatus.SERVED;
    }

    public void complete() {
        if (this.status != EatInOrderStatus.SERVED) {
            throw new IllegalStateException("준비 완료된 주문만 주문 완료할 수 있습니다.");
        }

        status = EatInOrderStatus.COMPLETED;
    }

    public OrderTable getOrderTable() {
        return this.orderTable;
    }

    public UUID getId() {
        return this.id;
    }

    public OrderLineItems getOrderLineItems() {
        return this.orderLineItems;
    }

    public EatInOrderStatus getStatus() {
        return this.status;
    }

    public LocalDateTime getOrderDateTime() {
        return this.orderDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EatInOrder eatInOrder)) {
            return false;
        }

        return this.getId() != null && Objects.equals(this.getId(), eatInOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
