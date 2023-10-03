package kitchenpos.deliveryorders.shared.dto.request;

import kitchenpos.deliveryorders.shared.dto.DeliveryOrderLineItemDto;

import java.util.List;

public class DeliveryOrderCreateRequest {
    private List<DeliveryOrderLineItemDto> orderLineItems;

    private String deliveryAddress;

    public DeliveryOrderCreateRequest(List<DeliveryOrderLineItemDto> orderLineItems, String deliveryAddress) {
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
    }

    public List<DeliveryOrderLineItemDto> getOrderLineItems() {
        return orderLineItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}
