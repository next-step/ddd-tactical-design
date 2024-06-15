package kitchenpos.eatinorder.tobe.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static kitchenpos.eatinorder.tobe.domain.EatInOrderStatus.*;

@Table
@Entity(name = "eat_in_orders")
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

    @Column(name = "tableId", nullable = false)
    private UUID orderTableId;


    public static EatInOrder of(LocalDateTime orderDateTime, OrderLineItems orderLineItems, UUID orderTableId) {
        return new EatInOrder(UUID.randomUUID(), WAITING, orderDateTime, orderLineItems, orderTableId);
    }

    private EatInOrder(UUID id, EatInOrderStatus status, LocalDateTime orderDateTime, OrderLineItems orderLineItems, UUID orderTableId) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    protected EatInOrder() {
    }

    public UUID getId() {
        return id;
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems.getOrderLineItems();
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public EatInOrder accept() {
        if (this.status != WAITING) {
            throw new IllegalStateException("주문 수락 가능한 상태가 아닙니다.");
        }
        this.status = ACCEPTED;
        return this;
    }

    public EatInOrder serve() {
        if (this.status != ACCEPTED) {
            throw new IllegalStateException("주문 서빙할 수 없는 상태입니다.");
        }
        this.status = SERVED;
        return this;
    }


    public EatInOrder complete() {
        if (this.status != SERVED) {
            throw new IllegalStateException("주문을 완료할 수 없는 상태입니다.");
        }
        this.status = COMPLETED;
        return this;
    }
}
