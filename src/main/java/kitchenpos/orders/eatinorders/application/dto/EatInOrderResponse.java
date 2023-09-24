package kitchenpos.orders.eatinorders.application.dto;

import kitchenpos.orders.eatinorders.domain.EatInOrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EatInOrderResponse {

    final private UUID id;
    final private EatInOrderStatus eatInOrderStatus;
    final private LocalDateTime orderDateTime;
    final private UUID orderTableId;
    final private List<EatInOrderLineItemResponse> orderLineItems;

    public EatInOrderResponse(UUID eatInOrderId, EatInOrderStatus eatInOrderStatus, LocalDateTime orderDateTime, UUID orderTableId, List<EatInOrderLineItemResponse> orderLineItems) {
        this.id = eatInOrderId;
        this.eatInOrderStatus = eatInOrderStatus;
        this.orderDateTime = orderDateTime;
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public UUID getId() {
        return id;
    }

    public EatInOrderStatus getEatInOrderStatus() {
        return eatInOrderStatus;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<EatInOrderLineItemResponse> getOrderLineItems() {
        return orderLineItems;
    }

}
