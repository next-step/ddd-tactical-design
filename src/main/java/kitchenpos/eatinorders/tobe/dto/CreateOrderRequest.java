package kitchenpos.eatinorders.tobe.dto;

import kitchenpos.eatinorders.tobe.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.OrderType;

import java.util.List;
import java.util.UUID;

public class CreateOrderRequest {
    private OrderType type;
    private OrderStatus status;
    private List<OrderLineItemRequest> orderLineItems;
    private String deliveryAddress;
    private UUID orderTableId;

    protected CreateOrderRequest() {}

    public OrderType getType() {
        return type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderLineItemRequest> getOrderLineItems() {
        return orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
