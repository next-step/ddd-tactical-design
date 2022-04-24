package kitchenpos.eatinorders.domain.tobe.domain;

import kitchenpos.eatinorders.domain.OrderLineItem;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderId;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "eatInOrders")
@Entity
public class EatInOrder {

    @EmbeddedId
    private OrderId id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @ManyToOne
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private TobeOrderTable orderTable;

    @Transient
    private OrderTableId orderTableId;

    protected EatInOrder() {
    }

    private EatInOrder(OrderId id, OrderStatus status, LocalDateTime orderDateTime, OrderLineItems orderLineItems, TobeOrderTable orderTable, OrderTableId orderTableId) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTable = orderTable;
        this.orderTableId = orderTableId;
    }

    public static EatInOrder Of (OrderLineItems orderLineItems, TobeOrderTable table) {
        if(Objects.isNull(orderLineItems)||orderLineItems.isEmpty()||Objects.isNull(table)) {
            throw new IllegalArgumentException();
        }
        if(table.isEmpty()) {
            throw new IllegalStateException();
        }
        return new EatInOrder(
            new OrderId(UUID.randomUUID()),
            OrderStatus.WAITING,
            LocalDateTime.now(),
            orderLineItems,
            table,
            table.getId()
        );
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderId getId() {
        return id;
    }

    public OrderTableId getOrderTableId() {
        return orderTableId;
    }

    public boolean isCompleted() {
        return status.isCompleted();
    }
}
