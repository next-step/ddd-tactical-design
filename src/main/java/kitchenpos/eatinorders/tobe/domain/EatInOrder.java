package kitchenpos.eatinorders.tobe.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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
    public OrderLineItems orderLineItems;

    @Column(name = "order_table_id", nullable = false)
    private String orderTableId;

    protected EatInOrder() {
    }

    public EatInOrder(OrderLineItems orderLineItems, String orderTableId) {
        this(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, orderTableId);
    }

    protected EatInOrder(UUID id, OrderStatus status, LocalDateTime orderDateTime,
        OrderLineItems orderLineItems, String orderTableId) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
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

    public String getOrderTableId() {
        return orderTableId;
    }
}
