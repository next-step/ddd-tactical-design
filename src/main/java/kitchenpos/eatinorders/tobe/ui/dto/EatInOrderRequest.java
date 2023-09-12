package kitchenpos.eatinorders.tobe.ui.dto;

import kitchenpos.eatinorders.tobe.domain.EatInOrderStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EatInOrderRequest {

    private final UUID id;

    @NotNull
    private EatInOrderStatus status;

    private final LocalDateTime orderDateTime;

    private final List<OrderLineItemRequest> orderLineItemRequests;

    private final UUID orderTableId;

    public EatInOrderRequest(UUID id, EatInOrderStatus status, LocalDateTime orderDateTime, List<OrderLineItemRequest> orderLineItemRequests, UUID orderTableId) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItemRequests = orderLineItemRequests;
        this.orderTableId = orderTableId;
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

    @NotNull
    public List<OrderLineItemRequest> getOrderLineItemRequests() {
        return orderLineItemRequests;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
