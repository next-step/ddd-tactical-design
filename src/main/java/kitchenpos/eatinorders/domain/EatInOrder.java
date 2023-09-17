package kitchenpos.eatinorders.domain;

import kitchenpos.common.domain.OrderDateTime;
import kitchenpos.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.eatinorders.exception.EatInOrderException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "eat_in_order")
@Entity
public class EatInOrder {
    @EmbeddedId
    private EatInOrderId id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Embedded
    private OrderDateTime orderDateTime;

    @Embedded
    private EatInOrderLineItems eatInOrderLineItems = new EatInOrderLineItems();

    @Embedded
    private OrderTableId orderTableId;

    protected EatInOrder() {
    }

    public EatInOrder(EatInOrderLineItems eatInOrderLineItems, OrderTableId orderTableId) {
        this.id = new EatInOrderId();
        this.status = EatInOrderStatus.WAITING;
        this.orderDateTime = OrderDateTime.now();
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.orderTableId = orderTableId;
    }

    public EatInOrder(EatInOrderStatus status, EatInOrderLineItems eatInOrderLineItems, OrderTableId orderTableId) {
        this.id = new EatInOrderId();
        this.status = status;
        this.orderDateTime = OrderDateTime.now();
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.orderTableId = orderTableId;
    }

    public EatInOrderId getId() {
        return id;
    }

    public UUID getIdValue() {
        return id.getId();
    }

    public LocalDateTime getOrderDateTimeValue() {
        return orderDateTime.getValue();
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
    }

    public OrderTableId getOrderTableId() {
        return orderTableId;
    }
}
