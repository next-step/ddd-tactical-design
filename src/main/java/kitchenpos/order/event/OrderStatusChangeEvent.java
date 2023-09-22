package kitchenpos.order.event;

import kitchenpos.order.domain.OrderStatus;

import java.util.UUID;

public class OrderStatusChangeEvent {

    private UUID orderId;
    private OrderStatus orderStatus;

    public OrderStatusChangeEvent(UUID orderId, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
