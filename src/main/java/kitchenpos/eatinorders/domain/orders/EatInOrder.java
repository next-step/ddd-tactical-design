package kitchenpos.eatinorders.domain.orders;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "eat_in_orders")
@Entity
public class EatInOrder extends AbstractAggregateRoot<EatInOrder> {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private EatInOrderLineItems orderLineItems;

    @Transient
    private UUID orderTableId;

    protected EatInOrder() {
    }

    private EatInOrder(UUID id, OrderStatus status, LocalDateTime orderDateTime, EatInOrderLineItems orderLineItems, UUID orderTableId) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public static EatInOrder waitingOrder(
            UUID uuid,
            LocalDateTime orderDateTime,
            EatInOrderLineItems orderLineItems,
            UUID orderTableId,
            OrderTableClient orderTableClient
    ) {
        orderTableClient.validOrderTableIdForOrder(orderTableId);
        return new EatInOrder(uuid, OrderStatus.WAITING, orderDateTime, orderLineItems, orderTableId);
    }

    public void accept() {
        if (this.status != OrderStatus.WAITING) {
            throw new IllegalStateException("대기 상태가 아닌 주문은 승인 상태로 변경할 수 없습니다. 주문상태: " + this.status);
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void served() {
        if (this.status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("승인 상태가 아닌 주문은 서빙 완료상태로 변경할 수 없습니다. 주문상태: " + this.status);
        }
        this.status = OrderStatus.SERVED;
    }

    public void complete(OrderTableClient orderTableClient) {
        if (this.status != OrderStatus.SERVED) {
            throw new IllegalStateException("서빙 완료가 아닌 주문은 완료상태로 변경할 수 없습니다. 주문상태: " + this.status);
        }
        this.status = OrderStatus.COMPLETED;
        orderTableClient.clearOrderTable(this.orderTableId);
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

    public EatInOrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
