package kitchenpos.order.tobe.eatinorder.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "eat_in_orders")
@Entity
public class EatInOrder {

    @Column(name = "eat_in_order_id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private EatInOrderLineItems orderLineItems;

    @Column(name = "order_table_id", columnDefinition = "binary(16)", nullable = false)
    private UUID orderTableId;

    protected EatInOrder() {
    }

    public EatInOrder(EatInOrderStatus status, LocalDateTime orderDateTime, List<EatInOrderLineItem> orderLineItems, UUID orderTableId) {
        this(status, orderDateTime, new EatInOrderLineItems(orderLineItems), orderTableId);
    }

    public EatInOrder(EatInOrderStatus status, LocalDateTime orderDateTime, EatInOrderLineItems orderLineItems, UUID orderTableId) {
        this.id = UUID.randomUUID();
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public static EatInOrder create(LocalDateTime orderDateTime, EatInOrderLineItems orderLineItems, UUID orderTableId) {
        return new EatInOrder(EatInOrderStatus.WAITING, orderDateTime, orderLineItems, orderTableId);
    }

    public void accept() {
        if (status != EatInOrderStatus.WAITING) {
            throw new IllegalStateException();
        }

        this.status = EatInOrderStatus.ACCEPTED;
    }

    public void serve() {
        if (status != EatInOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }

        this.status = EatInOrderStatus.SERVED;
    }

    public void complete() {
        if (status != EatInOrderStatus.SERVED) {
            throw new IllegalStateException();
        }

        this.status = EatInOrderStatus.COMPLETED;
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

    public EatInOrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
