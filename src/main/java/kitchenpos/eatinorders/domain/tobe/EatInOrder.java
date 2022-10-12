package kitchenpos.eatinorders.domain.tobe;

import java.time.LocalDateTime;
import java.util.Objects;
import kitchenpos.eatinorders.domain.OrderStatus;

public class EatInOrder {

    private final EatInOrderId id;

    private OrderStatus status = OrderStatus.WAITING;

    private final LocalDateTime orderDateTime;

    private OrderLineItems orderLineItems;

    private final OrderTable orderTable;

    public EatInOrder(
        final EatInOrderId id,
        final OrderLineItems orderLineItems,
        final OrderTable orderTable
    ) {
        this.id = id;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = orderLineItems;
        if (Objects.isNull(orderTable)) {
            throw new IllegalArgumentException("orderTable is required");
        }
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException("if orderTable is occupied, can not create order");
        }
        this.orderTable = orderTable;
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

    public void complete() {
        if (this.status != OrderStatus.SERVED) {
            throw new IllegalStateException("");
        }
        this.status = OrderStatus.COMPLETED;
        orderTable.clear();
    }

    public OrderStatus status() {
        return this.status;
    }

    public OrderTable orderTable() {
        return this.orderTable;
    }
}
