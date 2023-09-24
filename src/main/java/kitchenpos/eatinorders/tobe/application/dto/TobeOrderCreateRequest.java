package kitchenpos.eatinorders.tobe.application.dto;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItems;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public TobeOrderLineItems tobeOrderLineItems() {
        return new TobeOrderLineItems(orderLineItems.stream()
                                                    .map(TobeOrderLineItemRequest::toDomain)
                                                    .collect(Collectors.toList()));
    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<TobeOrderLineItemRequest> getOrderLineItems() {
        return orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
