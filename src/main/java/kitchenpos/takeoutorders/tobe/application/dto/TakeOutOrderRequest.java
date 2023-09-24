package kitchenpos.takeoutorders.tobe.application.dto;

import java.util.List;
import java.util.Objects;

public class TakeOutOrderRequest {
    private List<OrderLineItemRequest> orderLineItems;


    public TakeOutOrderRequest(List<OrderLineItemRequest> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public List<OrderLineItemRequest> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItemRequest> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public void validate() {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
