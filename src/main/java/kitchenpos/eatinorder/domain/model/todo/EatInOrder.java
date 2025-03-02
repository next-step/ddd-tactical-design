package kitchenpos.eatinorder.domain.model.todo;

import kitchenpos.eatinorder.domain.event.EatInOrderCompletedEvent;
import kitchenpos.eatinorder.domain.policy.CreateEatInOrderPolicy;
import kitchenpos.shared.domain.AggregateRoot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class EatInOrder extends AggregateRoot {
    private final EatInOrderId id;
    private EatInOrderStatus status;
    private final LocalDateTime orderDateTime;
    private final EatInOrderLineItems lineItems;
    private final OrderTableId orderTableId;

    private EatInOrder(
            final EatInOrderId id,
            final EatInOrderStatus status,
            final LocalDateTime orderDateTime,
            final EatInOrderLineItems lineItems,
            final OrderTableId orderTableId
    ) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.lineItems = lineItems;
        this.orderTableId = orderTableId;
    }

    public static EatInOrder create(
            final UUID id,
            final LocalDateTime orderDateTime,
            final List<EatInOrderLineItem> eatInOrderLineItems,
            final UUID orderTableId,
            final CreateEatInOrderPolicy createEatInOrderPolicy
    ) {
        createEatInOrderPolicy.validateOrderTableAvailability(orderTableId);
        return new EatInOrder(EatInOrderId.of(id), EatInOrderStatus.WAITING, orderDateTime, EatInOrderLineItems.of(eatInOrderLineItems), OrderTableId.of(orderTableId));
    }

    public static EatInOrder create(
            final UUID id,
            final LocalDateTime orderDateTime,
            final List<EatInOrderLineItem> eatInOrderLineItems,
            final UUID orderTableId,
            final EatInOrderStatus status
    ) {
        return new EatInOrder(EatInOrderId.of(id), status, orderDateTime, EatInOrderLineItems.of(eatInOrderLineItems), OrderTableId.of(orderTableId));
    }

    public void accept() {
        if (this.status != EatInOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        this.status = EatInOrderStatus.ACCEPTED;
    }

    public void serve() {
        if (this.status != EatInOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        this.status = EatInOrderStatus.SERVED;
    }

    public void complete() {
        if (this.status != EatInOrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        this.status = EatInOrderStatus.COMPLETED;
        this.registerEvent(new EatInOrderCompletedEvent(getId()));
    }

    public UUID getId() {
        return id.value();
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<EatInOrderLineItem> getLineItems() {
        return lineItems.getEatInOrderLineItems();
    }

    public UUID getOrderTableId() {
        return orderTableId.value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrder that = (EatInOrder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
