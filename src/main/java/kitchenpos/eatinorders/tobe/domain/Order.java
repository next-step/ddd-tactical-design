package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;

import java.util.List;

public class Order {
    private static final String NOT_WAITING_MESSAGE = "대기 상태에만 수락상태로 변경할 수 있습니다.";
    private static final String NOT_ACCEPT_MESSAGE = "수락 상태에만 서빙상태로 변경할 수 있습니다.";
    private final OrderType orderType;

    private final List<OrderLineItem> orderLineItems;

    private OrderStatus orderStatus;

    public Order(final OrderType orderType, final List<OrderLineItem> orderLineItems, final OrderStatus orderStatus) {
        this.orderType = orderType;
        this.orderLineItems = orderLineItems;
        this.orderStatus = orderStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void accept() {
        if (orderStatus != OrderStatus.WAITING) {
            throw new IllegalArgumentException(NOT_WAITING_MESSAGE);
        }
        orderStatus = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (orderStatus != OrderStatus.ACCEPTED) {
            throw new IllegalArgumentException(NOT_ACCEPT_MESSAGE);
        }
        orderStatus = OrderStatus.SERVED;
    }
}
