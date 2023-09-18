package kitchenpos.deliveryorders.tobe.application;

import kitchenpos.eatinorders.domain.OrderLineItem;

import java.util.List;
import java.util.Objects;

public class DeliveryRequest {

    private List<OrderLineItem> orderLineItems;

    private String deliveryAddress;

    public DeliveryRequest(final List<OrderLineItem> orderLineItems, final String deliveryAddress) {
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(final List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(final String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void validate() {
        validateOrderLineItems();
        validateQuantity();
        validateDeliveryAddress();
    }

    private void validateOrderLineItems() {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateQuantity() {
        orderLineItems.forEach(orderLineItem -> {
            if (orderLineItem.getQuantity() < 0) {
                throw new IllegalArgumentException();
            }
        });
    }

    private void validateDeliveryAddress() {
        if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
