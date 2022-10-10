package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "eat_in_orders")
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

    protected EatInOrder() {}

    public EatInOrder(OrderStatus status, LocalDateTime orderDateTime, OrderLineItems orderLineItems, OrderTable orderTable) {
        if (Objects.isNull(orderTable)) {
            throw new IllegalArgumentException("테이블을 선택해주세요.");
        }
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException("빈 테이블이 아닙니다. 빈 테이블을 선택해주세요.");
        }

        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
    }

    public EatInOrder(UUID id, OrderStatus status, LocalDateTime orderDateTime, OrderLineItems orderLineItems, OrderTable orderTable) {
        this(status, orderDateTime, orderLineItems, orderTable);
        this.id = id;
    }

    public EatInOrder(OrderLineItems orderLineItems, OrderTable orderTable) {
        this(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, orderTable);
    }

    public void accepted() {
        if (this.status != OrderStatus.WAITING) {
            throw new IllegalStateException("접수 대기 상태의 주문만 수락할 수 있습니다.");
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void served() {
        if (this.status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("접수 상태의 주문만 서빙할 수 있습니다.");
        }
        this.status = OrderStatus.SERVED;
    }

    public void completed() {
        if (this.status != OrderStatus.SERVED) {
            throw new IllegalStateException("서빙 완료 상태의 주문만 완료할 수 있습니다.");
        }
        this.status = OrderStatus.COMPLETED;
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
