package kitchenpos.eatinorders.application.dto;

import kitchenpos.eatinorders.domain.OrderStatus;

import java.util.UUID;

public class OrderStatusResponse {
    private UUID id;
    private OrderStatus status;

    private OrderStatusResponse(UUID id, OrderStatus status) {
        this.id = id;
        this.status = status;
    }

    public static OrderStatusResponse create(UUID id, OrderStatus status) {
        return new OrderStatusResponse(id, status);
    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
