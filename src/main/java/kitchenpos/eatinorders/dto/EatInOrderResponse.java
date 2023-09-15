package kitchenpos.eatinorders.dto;

import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EatInOrderResponse {

    private UUID eatInOrderId;
    private EatInOrderStatus eatInOrderStatus;
    private LocalDateTime orderDateTime;
    private UUID orderTableId;
    private List<EatInOrderLineItemResponse> orderLineItems;

    public static EatInOrderResponse fromEntity(EatInOrder eatInOrder) {
        return new EatInOrderResponse(
                eatInOrder.getIdValue(),
                eatInOrder.getStatus(),
                eatInOrder.getOrderDateTimeValue(),
                eatInOrder.getOrderTableIdValue(),
                EatInOrderLineItemResponse.fromEntities(eatInOrder.getOrderLineItemValues())
        );
    }

    public static List<EatInOrderResponse> fromEntities(List<EatInOrder> eatInOrder) {
        return eatInOrder.stream()
                .map(EatInOrderResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    public EatInOrderResponse(UUID eatInOrderId, EatInOrderStatus eatInOrderStatus, LocalDateTime orderDateTime, UUID orderTableId, List<EatInOrderLineItemResponse> orderLineItems) {
        this.eatInOrderId = eatInOrderId;
        this.eatInOrderStatus = eatInOrderStatus;
        this.orderDateTime = orderDateTime;
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public EatInOrderResponse() {

    }

    public UUID getEatInOrderId() {
        return eatInOrderId;
    }

    public void setEatInOrderId(UUID eatInOrderId) {
        this.eatInOrderId = eatInOrderId;
    }

    public EatInOrderStatus getEatInOrderStatus() {
        return eatInOrderStatus;
    }

    public void setEatInOrderStatus(EatInOrderStatus eatInOrderStatus) {
        this.eatInOrderStatus = eatInOrderStatus;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void setOrderTableId(UUID orderTableId) {
        this.orderTableId = orderTableId;
    }

    public List<EatInOrderLineItemResponse> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<EatInOrderLineItemResponse> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }
}
