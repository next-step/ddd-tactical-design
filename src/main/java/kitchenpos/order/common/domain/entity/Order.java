package kitchenpos.order.common.domain.entity;

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
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItems;
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

    protected Order() {}

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

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    public boolean isWaiting() {
        return this.getStatus() == OrderStatus.WAITING;
    }
    public boolean isAccepted() {
        return this.getStatus() == OrderStatus.ACCEPTED;
    }

    public void validateWaiting() {
        if(!isWaiting()) {
            throw new IllegalStateException("주문대기 되지 않았습니다.");
        }
    }

    public void validateAccepted() {
        if(!isAccepted()) {
            throw new IllegalStateException("주문수락 되지 않았습니다.");
        }
    }
}
