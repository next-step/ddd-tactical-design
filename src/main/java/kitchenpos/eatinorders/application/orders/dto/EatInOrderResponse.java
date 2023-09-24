package kitchenpos.eatinorders.application.orders.dto;

import kitchenpos.eatinorders.domain.orders.EatInOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EatInOrderResponse {
    private UUID id;
    private String status;
    private LocalDateTime orderDateTime;
    private List<EatInOrderLineItemResponse> orderLineItems;

    private UUID orderTableId;

    public EatInOrderResponse(UUID id, String status, LocalDateTime orderDateTime, List<EatInOrderLineItemResponse> orderLineItems, UUID orderTableId) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public static EatInOrderResponse from(EatInOrder order) {
        return new EatInOrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getOrderDateTime(),
                EatInOrderLineItemResponse.from(order.getOrderLineItems()),
                order.getOrderTableId()
        );
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

    public List<EatInOrderLineItemResponse> getOrderLineItems() {
        return orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
