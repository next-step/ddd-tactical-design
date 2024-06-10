package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.*;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class EatInOrder extends AbstractAggregateRoot<EatInOrder> {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @Column(name = "order_table_id", columnDefinition = "binary(16)")
    private UUID orderTableId;


    protected EatInOrder() {

    }

    public EatInOrder(OrderLineItems orderLineItems, UUID orderTableId) {
        this(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, orderTableId);
    }

    public EatInOrder(UUID id, OrderStatus status, LocalDateTime orderDateTime, OrderLineItems orderLineItems, UUID orderTableId) {
        checkOrderLineItem(orderLineItems);
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    private void checkOrderLineItem(OrderLineItems orderLineitems) {
        if (Objects.isNull(orderLineitems) || orderLineitems.isEmpty()) {
            throw new IllegalArgumentException("1개 이상의 주문항목이 필요합니다.");
        }
    }

    public void accepted() {
        if(!this.status.equals(OrderStatus.WAITING)){
            throw new IllegalStateException("대기상태에서만 수락할 수 있습니다.");
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void served() {
        if(!this.status.equals(OrderStatus.ACCEPTED)){
            throw new IllegalStateException("수락상태에서만 서빙할 수 있습니다.");
        }
        this.status = OrderStatus.SERVED;
    }

    public void complete() {
        if(!this.status.equals(OrderStatus.SERVED)){
            throw new IllegalStateException("서빙상태에서만 완료로 변경할 수 있습니다.");
        }
        this.status = OrderStatus.COMPLETED;
        registerEvent(new EatInOrderCompletedEvent(this.orderTableId));

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

    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public UUID getOrderTableId() {
        return this.orderTableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrder that = (EatInOrder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
