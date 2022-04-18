package kitchenpos.eatinorders.tobe.domain.order;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static kitchenpos.eatinorders.tobe.domain.order.OrderStatus.*;

public class Order {
    private static final int MIN_SIZE = 1;
    private static final String INVALID_MENU_MESSAGE = "주문할 메뉴가 존재하지 않습니다.";
    private static final String CANNOT_BE_ACCEPTED_ORDER = "접수할 수 없는 주문입니다.";
    private static final String CANNOT_BE_SERVED_ORDER = "서빙할 수 없는 주문입니다.";
    private static final String CANNOT_BE_COMPLETED_ORDER = "완료할 수 없는 주문입니다.";

    private final UUID id;
    private final List<OrderLineItem> orderLineItems;
    private final OrderTableId orderTableId;
    private OrderStatus status;

    public Order(OrderTableId orderTableId, OrderStatus status, OrderLineItem... orderLineItems) {
        validateItems(orderLineItems);
        this.id = UUID.randomUUID();
        this.orderTableId = orderTableId;
        this.status = status;
        this.orderLineItems = Arrays.asList(orderLineItems);
    }

    private void validateItems(OrderLineItem... orderLineItems) {
        if (Objects.isNull(orderLineItems) || orderLineItems.length < MIN_SIZE) {
            throw new IllegalArgumentException(INVALID_MENU_MESSAGE);
        }
    }

    public void accept() {
        if (this.status != WAITING) {
            throw new IllegalStateException(CANNOT_BE_ACCEPTED_ORDER);
        }
        this.status = ACCEPTED;
    }

    public void serve() {
        if (this.status != ACCEPTED) {
            throw new IllegalStateException(CANNOT_BE_SERVED_ORDER);
        }
        this.status = SERVED;
    }

    public void complete() {
        if (this.status != SERVED) {
            throw new IllegalStateException(CANNOT_BE_COMPLETED_ORDER);
        }
        this.status = COMPLETED;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
