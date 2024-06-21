package kitchenpos.orders.common.domain.tobe;

import jakarta.persistence.*;
import kitchenpos.orders.common.domain.OrderStatus;
import kitchenpos.orders.common.domain.OrderType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "orders")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Order {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    protected Order() {
    }

    public Order(OrderType type, List<OrderLineItem> orderLineItems) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.status = OrderStatus.WAITING;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = new OrderLineItems(orderLineItems);
    }

    public void accept() {
        if (status != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.SERVED;
    }

    public void complete() {
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.COMPLETED;
    }
}
