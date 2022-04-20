package kitchenpos.eatinorders.tobe.domain;

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
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class EatInOrder {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private EatInOrderLineItems eatInOrderLineItems;

    @ManyToOne
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    protected EatInOrder() {
    }

    public EatInOrder(final OrderTable orderTable, final List<EatInOrderLineItem> eatInOrderLineItems) {
        this.id = UUID.randomUUID();
        this.status = EatInOrderStatus.ACCEPTED;
        this.orderDateTime = LocalDateTime.now();
        this.orderTable = orderTable;
        this.eatInOrderLineItems = EatInOrderLineItems.of(eatInOrderLineItems);
    }

    private EatInOrder(final UUID id,
                      final EatInOrderStatus status,
                      final LocalDateTime orderDateTime,
                      final OrderTable orderTable,
                      final EatInOrderLineItems eatInOrderLineItems) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderTable = orderTable;
        this.eatInOrderLineItems = eatInOrderLineItems;
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
        return eatInOrderLineItems;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public UUID getOrderTableId() {
        return orderTable.getId();
    }

    public EatInOrder serve() {
        if(status.equals(EatInOrderStatus.ACCEPTED)) {
            return new EatInOrder(id, EatInOrderStatus.SERVED, orderDateTime, orderTable, eatInOrderLineItems);
        }

        throw new IllegalStateException("cannot serve order - order status is not 'ACCEPTED'");
    }

    public EatInOrder complete() {
        if(status.equals(EatInOrderStatus.SERVED)) {
            return new EatInOrder(id, EatInOrderStatus.COMPLETED, orderDateTime, orderTable.clear(), eatInOrderLineItems);
        }

        throw new IllegalStateException("cannot complete order - order status is not 'ACCEPTED'");
    }
}
