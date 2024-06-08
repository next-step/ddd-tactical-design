package kitchenpos.eatinorders.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import kitchenpos.common.domain.orders.OrderStatus;
import kitchenpos.common.domain.ordertables.OrderType;


public class OrderRequest {

    private UUID id;

    private OrderType type;

    private OrderStatus status;

    private LocalDateTime orderDateTime;

    private List<OrderLineItemRequest> orderLineItemRequests;

    private String deliveryAddress;

    private OrderTableRequest orderTableRequest;

    private UUID orderTableId;

    public OrderRequest() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(final OrderType type) {
        this.type = type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(final OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(final LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public List<OrderLineItemRequest> getOrderLineItems() {
        return orderLineItemRequests;
    }

    public void setOrderLineItems(final List<OrderLineItemRequest> orderLineItemRequests) {
        this.orderLineItemRequests = orderLineItemRequests;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(final String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public OrderTableRequest getOrderTable() {
        return orderTableRequest;
    }

    public void setOrderTable(final OrderTableRequest orderTableRequest) {
        this.orderTableRequest = orderTableRequest;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void setOrderTableId(final UUID orderTableId) {
        this.orderTableId = orderTableId;
    }
}
