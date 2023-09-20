package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.eatinorders.exception.EatInOrderException;
import kitchenpos.eatinorders.publisher.EatInOrderCompletedEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "eat_in_order")
@Entity
public class EatInOrder extends AbstractAggregateRoot<EatInOrder> {
    @EmbeddedId
    private EatInOrderId id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems();

    @Embedded
    private OrderTableId orderTableId;

    protected EatInOrder() {
    }

    public EatInOrder(EatInOrderLineItems eatInOrderLineItems, OrderTableId orderTableId) {
        this.id = new EatInOrderId();
        this.status = EatInOrderStatus.WAITING;
        this.orderDateTime = LocalDateTime.now();
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.orderTableId = orderTableId;
    }

    public EatInOrder(EatInOrderStatus status, EatInOrderLineItems eatInOrderLineItems, OrderTableId orderTableId) {
        this.id = new EatInOrderId();
        this.status = status;
        this.orderDateTime = LocalDateTime.now();
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.orderTableId = orderTableId;
    }

    public void accept() {
        if (!status.isWaiting()) {
            throw new EatInOrderException(EatInOrderErrorCode.IS_NOT_WAITING);
        }
        this.status = EatInOrderStatus.ACCEPTED;
    }

    public void serve() {
        if (!status.isAccepted()) {
            throw new EatInOrderException(EatInOrderErrorCode.IS_NOT_ACCEPTED);
        }
        this.status = EatInOrderStatus.SERVED;
    }

    public void complete() {
        if (!status.isServed()) {
            throw new EatInOrderException(EatInOrderErrorCode.IS_NOT_SERVED);
        }
        this.status = EatInOrderStatus.COMPLETED;
        completed();
    }

    @PostUpdate
    private void completed() {
        registerEvent(new EatInOrderCompletedEvent(this, orderTableId.getValue()));
    }

    public OrderTableId getOrderTableId() {
        return orderTableId;
    }

    public UUID getOrderTableIdValue() {
        return orderTableId.getValue();
    }

    public List<EatInOrderLineItem> getOrderLineItemValues() {
        return eatInOrderLineItems.getEatInOrderLineItems();
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public EatInOrderId getId() {
        return id;
    }

    public UUID getIdValue() {
        return id.getId();
    }

    public LocalDateTime getOrderDateTimeValue() {
        return orderDateTime;
    }
}
