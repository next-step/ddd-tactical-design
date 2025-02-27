package kitchenpos.eatinorder.domain.model.todo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class EatInOrder {
    private final UUID id;
    private EatInOrderStatus status;
    private final LocalDateTime orderDateTime;
    private final EatInOrderLineItems lineItems;
    private final OrderTableId orderTableId;

    private EatInOrder(
            final UUID id,
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
            final UUID orderTableId
    ) {
        return new EatInOrder(id, EatInOrderStatus.WAITING, orderDateTime, EatInOrderLineItems.of(eatInOrderLineItems), OrderTableId.of(orderTableId));
    }

    public static EatInOrder create(
            final UUID id,
            final LocalDateTime orderDateTime,
            final List<EatInOrderLineItem> eatInOrderLineItems,
            final UUID orderTableId,
            final EatInOrderStatus status
    ) {
        return new EatInOrder(id, status, orderDateTime, EatInOrderLineItems.of(eatInOrderLineItems), OrderTableId.of(orderTableId));
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

    public UUID getId() {
        return id;
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
