package kitchenpos.eatinorders.tobe.application.dto;

import kitchenpos.eatinorders.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TobeOrderCreateRequest {
    private final UUID id;
    private final OrderStatus status;
    private final LocalDateTime orderDateTime;
    private final List<TobeOrderLineItemRequest> orderLineItems;
    private final UUID orderTableId;

    public TobeOrderCreateRequest(final UUID id, final OrderStatus status, final LocalDateTime orderDateTime,
                                  final List<TobeOrderLineItemRequest> orderLineItems,
                                  final UUID orderTableId) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<TobeOrderLineItemRequest> getOrderLineItems() {
        return orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
