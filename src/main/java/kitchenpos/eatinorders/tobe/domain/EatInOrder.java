package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.eatinorders.tobe.domain.constant.EatInOrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    private EatInOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @Column(name = "order_table_id", columnDefinition = "binary(16)")
    private UUID orderTableId;

    protected EatInOrder() {
    }

    private EatInOrder(UUID id, EatInOrderStatus status, LocalDateTime orderDateTime, OrderLineItems orderLineItems, UUID orderTableId) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public static EatInOrder startWaiting(
            UUID orderTableId,
            OrderLineItems orderLineItems
    ) {

        return new EatInOrder(
                UUID.randomUUID(),
                EatInOrderStatus.WAITING,
                LocalDateTime.now(),
                orderLineItems,
                orderTableId
        );

    }
}
