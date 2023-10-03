package kitchenpos.deliveryorders.shared.dto;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class DeliveryOrderDto {
    private UUID id;
    private OrderType type;
    private OrderStatus status;

    private LocalDateTime orderDateTime;
    private String deliveryAddress;

    private List<DeliveryOrderLineItemDto> orderLineItems;

    public List<DeliveryOrderLineItemDto> getOrderLineItems() {
        return orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public UUID getId() {
        return id;
    }

    public OrderType getType() {
        return type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }
}
