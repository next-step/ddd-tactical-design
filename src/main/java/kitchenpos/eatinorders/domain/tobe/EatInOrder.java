package kitchenpos.eatinorders.domain.tobe;

import java.time.LocalDateTime;
import java.util.Objects;
import kitchenpos.eatinorders.domain.OrderStatus;

public class EatInOrder {

    private final EatInOrderId id;

    private OrderStatus status = OrderStatus.WAITING;

    private final LocalDateTime orderDateTime;

    private OrderLineItems orderLineItems;

    private final OrderTableId orderTableId;

    public EatInOrder(
        final EatInOrderId id,
        final OrderLineItems orderLineItems,
        final OrderTableId orderTableId,
        final EatInOrderCreatePolicy eatInOrderCreatePolicy
    ) {
        this.id = id;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = orderLineItems;
        if (Objects.isNull(orderTableId)) {
            throw new IllegalArgumentException("orderTableId is required");
        }
        if (eatInOrderCreatePolicy.isOccupiedOrderTable(orderTableId)) {
            throw new IllegalStateException("orderTable cannot be empty");
        }
        this.orderTableId = orderTableId;
    }

    public void accept() {
        if (this.status != OrderStatus.WAITING) {
            throw new IllegalStateException("");
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (this.status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("");
        }
        this.status = OrderStatus.SERVED;
    }

    public void complete(OrderTableCleanPolicy orderTableCleanPolicy) {
        if (this.status != OrderStatus.SERVED) {
            throw new IllegalStateException("");
        }
        this.status = OrderStatus.COMPLETED;
        orderTableCleanPolicy.clear(orderTableId);
    }

    public OrderStatus status() {
        return this.status;
    }

    public OrderTableId orderTableId() {
        return this.orderTableId;
    }
}
