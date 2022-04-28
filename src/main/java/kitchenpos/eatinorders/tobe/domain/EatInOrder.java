package kitchenpos.eatinorders.tobe.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import org.springframework.data.annotation.Transient;

@Table(name = "orders")
@Entity
public class EatInOrder {

    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @GeneratedValue
    private UUID id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private final OrderType type = OrderType.EAT_IN;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @Transient
    private UUID orderTableId;

    protected EatInOrder() { }

    public EatInOrder(OrderLineItems orderLineItems, UUID orderTableId) {
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
        this.status = OrderStatus.WAITING;
        validCreateOrder();
    }

    private void validCreateOrder() {
        if (orderTableId == null) {
            throw new IllegalArgumentException("주문을 생성하기 위해선 주문 테이블 정보가 필요합니다.");
        }
    }

    public void accept() {
        if (OrderStatus.WAITING != status) {
            throw new IllegalStateException("대기 상태의 주문만 접수 상태로 변경할 수 있습니다.");
        }

        this.status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (OrderStatus.ACCEPTED != status) {
            throw new IllegalStateException("접수 상태의 주문만 서빙 상태로 변경할 수 있습니다.");
        }

        this.status = OrderStatus.SERVED;
    }

    public void complete(OrderTable orderTable) {
        if (orderTable == null || !orderTableId.equals(orderTable.getId())) {
            throw new IllegalStateException("유효하지 않은 주문 테이블입니다.");
        }

        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException("서빙 상태의 주문만 완료 상태로 변경할 수 있습니다.");
        }

        status = OrderStatus.COMPLETED;

        if (orderTable.isCompletedAllOrders()) {
            orderTable.clear();
        }
    }

    public boolean isCompletedOrder() {
        return OrderStatus.COMPLETED == this.status;
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

    public UUID getOrderTableId() {
        return orderTableId;
    }

    @PrePersist
    private void onPersist() {
        orderDateTime = LocalDateTime.now();
    }
}
