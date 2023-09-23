package kitchenpos.orders.eatinorders.dto;

import kitchenpos.orders.eatinorders.domain.EatInOrder;
import kitchenpos.orders.eatinorders.domain.EatInOrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EatInOrderResponse {

    private UUID id;
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
        this.id = eatInOrderId;
        this.eatInOrderStatus = eatInOrderStatus;
        this.orderDateTime = orderDateTime;
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public EatInOrderResponse() {

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
