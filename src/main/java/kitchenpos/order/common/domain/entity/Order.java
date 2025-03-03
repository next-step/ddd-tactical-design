package kitchenpos.order.common.domain.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.order.common.domain.exception.OrderInvalidException;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItems;
import kitchenpos.order.eatin.domain.model.OrderTableId;
import org.hibernate.annotations.DynamicUpdate;

@Table(name = "orders")
@Entity
@DynamicUpdate
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Order {

    @EmbeddedId
    private OrderId orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "varchar(255)", insertable = false, updatable = false)
    private OrderType type;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "order_table_id"))
    private OrderTableId orderTableId;

    protected Order() {}

    public Order(OrderId orderId, OrderType type, OrderStatus status, LocalDateTime orderDateTime,
        OrderLineItems orderLineItems, OrderTableId orderTableId) {
        this.orderId = orderId;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public OrderId getOrderId() {
        return orderId;
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

    public OrderTableId getOrderTableId() {
        return orderTableId;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    public boolean isDelivery() {
        return this.getType() == OrderType.DELIVERY;
    }

    public boolean isEatIn() {
        return this.getType() == OrderType.EAT_IN;
    }

    public boolean isTakeout() {
        return this.getType() == OrderType.TAKEOUT;
    }

    public boolean isWaiting() {
        return this.getStatus() == OrderStatus.WAITING;
    }
    public boolean isAccepted() {
        return this.getStatus() == OrderStatus.ACCEPTED;
    }

    public boolean isDelivered() {
        return this.getStatus() == OrderStatus.DELIVERED;
    }
    public boolean isServed() {
        return this.getStatus() == OrderStatus.SERVED;
    }

    public void validateIsWaiting() {
        if(!isWaiting()) {
            throw new OrderInvalidException(ErrorCode.ORDER_STATUS_IS_NOT_WAITING.toString());
        }
    }

    public void validateIsAccepted() {
        if(!isAccepted()) {
            throw new OrderInvalidException(ErrorCode.ORDER_STATUS_IS_NOT_ACCEPTED.toString());
        }
    }

    public void validateOrderCompletion() {
        if (isDelivery() && !isDelivered()) {
            throw new OrderInvalidException(ErrorCode.ORDER_STATUS_IS_NOT_DELIVERED.toString());
        }
        if ((isTakeout() || isEatIn()) && !isServed()) {
            throw new OrderInvalidException(ErrorCode.ORDER_STATUS_IS_NOT_SERVED.toString());
        }
    }
}
