package kitchenpos.eatinorders.order.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import kitchenpos.eatinorders.order.domain.EatInOrder;
import kitchenpos.eatinorders.order.domain.OrderLineItems;

public class OrderResponse {
    private UUID id;
    private String status;
    private LocalDateTime orderDateTime;
    private List<OrderLineItemResponse> eatInOrderLineItems;
    private UUID orderTableId;

    public OrderResponse(UUID id, String status, LocalDateTime orderDateTime, List<OrderLineItemResponse> eatInOrderLineItems, UUID orderTableId) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.orderTableId = orderTableId;
    }

    public OrderResponse(EatInOrder order) {
        this(order.getId(), order.getStatus().name(), order.getOrderDateTime(), toOrderLineItemResponses(order.getOrderLineItems()), order.getOrderTable().getId());
    }

    private static List<OrderLineItemResponse> toOrderLineItemResponses(OrderLineItems orderLineItems) {
        return orderLineItems.listValue()
                .stream()
                .map(OrderLineItemResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public UUID getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<OrderLineItemResponse> getEatInOrderLineItems() {
        return eatInOrderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
