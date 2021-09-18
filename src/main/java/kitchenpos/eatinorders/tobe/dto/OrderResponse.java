package kitchenpos.eatinorders.tobe.dto;

import kitchenpos.eatinorders.tobe.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.OrderType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderResponse {
    private UUID id;
    private OrderType type;
    private OrderStatus status;
    private LocalDateTime orderDateTime;
    private List<OrderLineItemResponse> orderLineItems;
    private String deliveryAddress;
    private OrderTableResponse orderTable;
    private UUID orderTableId;

    protected OrderResponse() {}

    public OrderResponse(final UUID id, final OrderType type, final OrderStatus status, final LocalDateTime orderDateTime, final List<OrderLineItemResponse> orderLineItems, final String deliveryAddress, final OrderTableResponse orderTable, final UUID orderTableId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.orderTable = orderTable;
        this.orderTableId = orderTableId;
    }

    public UUID getId() {
        return id;
    }

    public OrderType getType() {
        return type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<OrderLineItemResponse> getOrderLineItems() {
        return orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public OrderTableResponse getOrderTable() {
        return orderTable;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
