package kitchenpos.eatinorders.application.dto;

import kitchenpos.eatinorders.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EatInOrderResponse {
    private UUID id;
    private OrderStatus status;
    private LocalDateTime orderDateTime;

    private UUID orderTableId;
    private List<EatInOrderLineItemResponse> eatInOrderLineItems;

    private EatInOrderResponse(
            UUID id, OrderStatus status, LocalDateTime orderDateTime,
            UUID orderTableId, List<EatInOrderLineItemResponse> eatInOrderLineItems) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderTableId = orderTableId;
        this.eatInOrderLineItems = eatInOrderLineItems;
    }


    public static EatInOrderResponse create(
            UUID id, OrderStatus status, LocalDateTime orderDateTime,
            UUID orderTableId, List<EatInOrderLineItemResponse> eatInOrderLineItems) {
        return new EatInOrderResponse(id, status, orderDateTime, orderTableId, eatInOrderLineItems);
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

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<EatInOrderLineItemResponse> getEatInOrderLineItems() {
        return eatInOrderLineItems;
    }
}
