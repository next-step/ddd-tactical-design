package kitchenpos.eatinorders.tobe.domain.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Table(name = "orders")
@Entity(name = "tobeOrders")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
public abstract class Order {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(
        name = "order_table_id",
        columnDefinition = "varbinary(16)"
    )
    private UUID orderTableId;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private OrderLineItems orderLineItems;

    protected Order() {
    }

    public Order(final OrderLineItem... orderLineItems) {
        this(Arrays.asList(orderLineItems));
    }

    public Order(final List<OrderLineItem> orderLineItems) {
        this.id = UUID.randomUUID();
        this.orderDateTime = LocalDateTime.now();
        this.status = OrderStatus.WAITING;
        this.orderLineItems = new OrderLineItems(orderLineItems);
    }

    Order(final OrderStatus orderStatus, final OrderLineItem... orderLineItems) {
        this(orderStatus, Arrays.asList(orderLineItems));
    }

    Order(final OrderStatus orderStatus, final List<OrderLineItem> orderLineItems) {
        this.id = UUID.randomUUID();
        this.orderDateTime = LocalDateTime.now();
        this.status = orderStatus;
        this.orderLineItems = new OrderLineItems(orderLineItems);
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public Order accept() {
        if (status != OrderStatus.WAITING) {
            throw new IllegalStateException("대기 상태에서만 주문을 승인할 수 있습니다.");
        }
        status = OrderStatus.ACCEPTED;
        return this;
    }

    public Order serve() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("승인 상태에서만 주문을 서빙할 수 있습니다.");
        }
        status = OrderStatus.SERVED;
        return this;
    }

    public Order complete() {
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException("서빙된 상태에서만 주문을 완료할 수 있습니다.");
        }
        status = OrderStatus.COMPLETED;
        return this;
    }

    public abstract UUID getOrderTableId();

    public abstract OrderType getType();
}
