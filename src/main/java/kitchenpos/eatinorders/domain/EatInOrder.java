package kitchenpos.eatinorders.domain;

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
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

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
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    protected EatInOrder() {
    }

    public EatInOrder(UUID id, OrderLineItems orderLineItems, OrderTable orderTable) {
        if (orderTable.isNotInUse()) {
            throw new IllegalStateException(String.format("매장주문 시 주문테이블은 사용중이어야 합니다. 현재 값: %s", orderTable.isInUse()));
        }
        this.id = id;
        this.status = OrderStatus.WAITING;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
    }

    public void accept() {
        if (status != OrderStatus.WAITING) {
            throw new IllegalStateException(String.format("대기중인 매장주문만 접수할 수 있습니다. 현재 값: %s", status));
        }
        status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException(String.format("접수된 매장주문만 서빙할 수 있습니다. 현재 값: %s", status));
        }
        status = OrderStatus.SERVED;
    }

    public void complete(EatInOrderCompletePolicy eatInOrderCompletePolicy) {
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException(String.format("서빙된 매장주문만 완료할 수 있습니다. 현재 값: %s", status));
        }
        status = OrderStatus.COMPLETED;
        eatInOrderCompletePolicy.follow(orderTable);
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

    public OrderTable getOrderTable() {
        return orderTable;
    }
}
