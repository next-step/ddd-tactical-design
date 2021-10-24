package kitchenpos.eatinorders.tobe.ui;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.tobe.domain.TobeOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderForm {
    private UUID id;
    private OrderType type;
    private OrderStatus status;
    private LocalDateTime orderDateTime;
    private List<OrderLineItemForm> orderLineItems;
    private String deliveryAddress;
    private UUID orderTableId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public List<OrderLineItemForm> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItemForm> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void setOrderTableId(UUID orderTableId) {
        this.orderTableId = orderTableId;
    }

    public static OrderForm of(TobeOrder order) {
        OrderForm orderForm = new OrderForm();
        orderForm.setId(order.getId());
        orderForm.setOrderDateTime(order.getOrderDateTime());
        orderForm.setStatus(order.getStatus());
        orderForm.setType(order.getType());
        orderForm.setDeliveryAddress(order.getDeliveryAddress());
        orderForm.setOrderTableId(order.getOrderTableId());
        orderForm.setOrderLineItems(order.getOrderLineItems().stream().map(OrderLineItemForm::of).collect(Collectors.toList()));
        return orderForm;
    }
}
