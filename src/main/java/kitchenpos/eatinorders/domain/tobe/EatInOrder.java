package kitchenpos.eatinorders.domain.tobe;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static kitchenpos.eatinorders.domain.tobe.OrderStatus.*;

@Table(name = "eatin_orders")
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
    private EatInOrderLineItems eatInOrderLineItems;

    @ManyToOne
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    protected EatInOrder() {
    }

    public EatInOrder(List<EatInOrderLineItem> eatInOrderLineItems, OrderTable orderTable) {
        this(UUID.randomUUID(), WAITING, now(), eatInOrderLineItems, orderTable);
    }

    public EatInOrder(UUID id, OrderStatus status, LocalDateTime orderDateTime, List<EatInOrderLineItem> eatInOrderLineItems, OrderTable orderTable) {
        validateOrderTable(orderTable);
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.eatInOrderLineItems = new EatInOrderLineItems(eatInOrderLineItems);
        this.orderTable = orderTable;
    }

    private void validateOrderTable(OrderTable orderTable) {
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
    }

    public void accept() {
        if (!status.isWaiting()) {
            throw new IllegalStateException();
        }
        status = ACCEPTED;
    }

    public void serve() {
        if (!status.isAcepted()) {
            throw new IllegalStateException();
        }
        status = SERVED;
    }

    public void complate(OrderManager orderManager) {
        if (!status.isServed()) {
            throw new IllegalStateException();
        }
        status = COMPLETED;
        orderTableClear(orderManager);
    }

    private void orderTableClear(OrderManager orderManager) {
        if (!orderManager.isExistNotComplateOrder(orderTable)) {
            orderTable.clear();
        }
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

    public List<EatInOrderLineItem> getEatInOrderLineItems() {
        return eatInOrderLineItems.getEatInOrderLineItem();
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }
}
