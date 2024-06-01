package kitchenpos.eatinorders.tobe;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private EatInOrderType type;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @Transient
    private UUID orderTableId;

    public EatInOrder(UUID id, EatInOrderType type, EatInOrderStatus status, LocalDateTime orderDateTime,
                      OrderLineItems orderLineItems, UUID orderTableId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public void accept() {
        if (status != EatInOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        status = EatInOrderStatus.ACCEPTED;
    }

    public void serve() {
        if (status != EatInOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        status = EatInOrderStatus.SERVED;
    }

    public void complete() {
        if (status != EatInOrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        status = EatInOrderStatus.COMPLETED;
    }

    public UUID getId() {
        return id;
    }

    public EatInOrderType getType() {
        return type;
    }

    public EatInOrderStatus getStatus() {
        return status;
    }
}
