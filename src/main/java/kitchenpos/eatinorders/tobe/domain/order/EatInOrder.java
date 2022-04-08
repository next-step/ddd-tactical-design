package kitchenpos.eatinorders.tobe.domain.order;

import java.time.LocalDateTime;
import java.util.Objects;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableId;

public final class EatInOrder {

    private final OrderId id;
    private OrderStatus status;
    private final OrderTableId orderTableId;
    private final LocalDateTime orderDateTime;
    private final OrderLineItems orderLineItems;

    public EatInOrder(
        OrderId id,
        OrderStatus status,
        OrderTableId orderTableId,
        LocalDateTime orderDateTime,
        OrderLineItems orderLineItems
    ) {
        if (Objects.isNull(orderTableId)) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.status = status;
        this.orderTableId = orderTableId;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
    }

    public EatInOrder(
        OrderId id,
        OrderTableId orderTableId,
        OrderLineItem... orderLineItems
    ) {
        this(
            id,
            OrderStatus.WAITING,
            orderTableId,
            LocalDateTime.now(),
            new OrderLineItems(orderLineItems)
        );
    }

    public void accept() {
        if (this.status != OrderStatus.WAITING) {
            throw new IllegalStateException("WAITING 상태가 아니면 접수가 불가능합니다. 현재 상태: " + status);
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (this.status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("ACCEPTED 상태가 아니면 서빙이 불가능합니다. 현재 상태: " + status);
        }
        this.status = OrderStatus.SERVED;
    }

    public void complete() {
        if (this.status != OrderStatus.SERVED) {
            throw new IllegalStateException("SERVED 상태가 아니면 완료가 불가능합니다. 현재 상태: " + status);
        }
        this.status = OrderStatus.COMPLETED;
    }
}
