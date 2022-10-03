package kitchenpos.eatinorders.tobe.domain;

import java.time.LocalDateTime;
import java.util.UUID;
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
import kitchenpos.eatinorders.domain.OrderType;

@Table(name = "tb_orders")
@Entity(name = "tb_orders")
public class EatInOrder {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems = new OrderLineItems();

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table"),
            insertable = false, updatable = false
    )
    private OrderTable orderTable;

    protected EatInOrder() {
        id = UUID.randomUUID();
        type = OrderType.EAT_IN;
        status = OrderStatus.WAITING;
        orderDateTime = LocalDateTime.now();
    }

    public EatInOrder(
            OrderLineItems orderLineItems,
            OrderTable orderTable
    ) {
        this();
        validate(orderTable);
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
    }

    private void validate(OrderTable orderTable) {
        if (orderTable.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    public void accept() {
        status = status.accept();
    }

    public void serve() {
        status = status.serve();
    }

    public void complete() {
        status = status.complete();

        if (orderTable.isCompletedAllOrders()) {
            orderTable.clear();
        }
    }

    public boolean isCompleted() {
        return OrderStatus.COMPLETED == status;
    }

    public UUID getId() {
        return id;
    }

    public OrderType getType() {
        return type;
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
