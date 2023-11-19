package kitchenpos.order.tobe.eatinorder.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrder;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus;

public class DetailEatInOrderResponse {

    private UUID id;
    private EatInOrderStatus status;
    private LocalDateTime orderDateTime;
    private List<EatInOrderLineItemDto> orderLineItems;
    private UUID orderTableId;

    public DetailEatInOrderResponse(UUID id, EatInOrderStatus status, LocalDateTime orderDateTime, List<EatInOrderLineItemDto> orderLineItems,
        UUID orderTableId) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public static DetailEatInOrderResponse of(EatInOrder entity) {
        var orderLineItems = entity.getOrderLineItems().getValue()
            .stream()
            .map(EatInOrderLineItemDto::of)
            .collect(Collectors.toList());

        return new DetailEatInOrderResponse(
            entity.getId(),
            entity.getStatus(),
            entity.getOrderDateTime(),
            orderLineItems,
            entity.getOrderTableId()
        );
    }

    public UUID getId() {
        return id;
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<EatInOrderLineItemDto> getOrderLineItems() {
        return orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
