package kitchenpos.eatinorders.tobe.dto;

import kitchenpos.eatinorders.tobe.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.OrderType;
import kitchenpos.eatinorders.tobe.exception.IllegalOrderException;

import java.util.List;
import java.util.Objects;
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

    public void validate() {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalOrderException("한개 이상의 메뉴를  주문해야합니다.");
        }
    }
}
