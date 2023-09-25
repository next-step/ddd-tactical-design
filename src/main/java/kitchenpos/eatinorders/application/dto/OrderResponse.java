package kitchenpos.eatinorders.application.dto;

import kitchenpos.eatinorders.domain.order.Order;
import kitchenpos.eatinorders.domain.order.OrderStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderResponse {

    private final UUID orderId;

    private final UUID orderTableId;

    private OrderStatus status;

    private LocalDateTime orderDateTime;

    private final List<OrderLineItemResponse> orderLineItemResponses;

    public OrderResponse(final Order order) {
        this.orderId = order.getId();
        this.orderTableId = order.getOrderTableId();
        this.orderLineItemResponses = OrderLineItemResponse.ofList(order.getOrderLineItems());
        this.status = order.getStatus();
        this.orderDateTime = order.getOrderDateTime();
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<OrderLineItemResponse> getOrderLineItemResponses() {
        return orderLineItemResponses;
    }
}
