package kitchenpos.eatinorders.domain.tobe.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "eat_in_orders")
@Entity
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Column(name = "order_table_id", columnDefinition = "binary(16)")
    private UUID orderTableId;
    @Embedded
    private EatInOrderLineItems orderLineItems;

    protected EatInOrder() {
    }

    public EatInOrder(EatInOrderLineItems orderLineItems, UUID orderTableId) {
        if (orderTableId == null) {
            throw new IllegalArgumentException("테이블이 없으면 매장식사 주문을 할 수 없습니다.");
        }
        this.id = UUID.randomUUID();
        this.status = EatInOrderStatus.initialOrderStatus();
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public void accept() {
        if (status != EatInOrderStatus.WAITING) {
            throw new IllegalStateException("접수 대기 중인 주문만 접수할 수 있다.");
        }
        nextStatus();
    }

    public void serve() {
        if (status != EatInOrderStatus.ACCEPTED) {
            throw new IllegalStateException("접수된 주문만 서빙할 수 있다.");
        }
        nextStatus();
    }

    public void complete() {
        if (status != EatInOrderStatus.SERVED) {
            throw new IllegalStateException("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.");
        }
        nextStatus();
    }

    private void nextStatus() {
        status = status.nextStatus();
    }

    public boolean isSameStatus(EatInOrderStatus orderStatus) {
        return status == orderStatus;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
