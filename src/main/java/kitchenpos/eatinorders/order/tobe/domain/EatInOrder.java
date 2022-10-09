package kitchenpos.eatinorders.order.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class EatInOrder {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderTableId;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Embedded
    private EatInOrderLineItems eatInOrderLineItems;

    protected EatInOrder() {
    }

    private EatInOrder(final UUID id,
                       final UUID orderTableId,
                       final LocalDateTime orderDateTime,
                       final EatInOrderStatus status,
                       final EatInOrderLineItems eatInOrderLineItems) {
        this.id = id;
        this.orderTableId = orderTableId;
        this.orderDateTime = orderDateTime;
        this.status = status;
        this.eatInOrderLineItems = eatInOrderLineItems;
    }

    static EatInOrder create(final UUID orderTableId, final EatInOrderLineItems eatInOrderLineItems) {
        final EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), orderTableId, LocalDateTime.now(), EatInOrderStatus.WAITING, eatInOrderLineItems);
        eatInOrder.eatInOrderLineItems.makeRelations(eatInOrder);
        return eatInOrder;
    }

    public UUID id() {
        return id;
    }

    public UUID orderTableId() {
        return orderTableId;
    }

    public EatInOrderStatus status() {
        return status;
    }

    public LocalDateTime orderDateTime() {
        return orderDateTime;
    }

    public EatInOrderLineItems eatInOrderLineItems() {
        return eatInOrderLineItems;
    }
}