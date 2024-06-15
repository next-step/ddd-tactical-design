package kitchenpos.eatinorder.tobe.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        return new EatInOrder(UUID.randomUUID(), EatInOrderStatus.WAITING, orderDateTime, orderLineItems, orderTableId);
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

}
