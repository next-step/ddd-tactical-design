package kitchenpos.eatinorders.tobe.dto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.model.OrderType;

public class OrderResponse {

    private UUID id;
    private OrderType type;
    private OrderStatus status;
    private LocalDateTime orderDateTime;
    private List<OrderLineItem> orderLineItems;
    private String deliveryAddress;
    private OrderTable orderTable;
    private UUID orderTableId;

    protected OrderResponse() {
    }

    public OrderResponse(final UUID id, final OrderType type, final OrderStatus status, final LocalDateTime orderDateTime, final List<OrderLineItem> orderLineItems, final String deliveryAddress,
        final OrderTable orderTable, final UUID orderTableId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = Collections.unmodifiableList(orderLineItems);
        this.deliveryAddress = deliveryAddress;
        this.orderTable = orderTable;
        this.orderTableId = orderTableId;
    }

    public OrderResponse(final UUID id, final OrderType type, final OrderStatus status, final LocalDateTime orderDateTime, final OrderLineItems orderLineItems, final OrderTable orderTable, final UUID orderTableId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems.getOrderLineItems();
        this.orderTable = orderTable;
        this.orderTableId = orderTableId;
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

    public OrderTable getOrderTable() {
        return orderTable;
    }

}
