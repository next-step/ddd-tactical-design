package kitchenpos.deliveryorders.dto;

import kitchenpos.support.dto.OrderLineItemCreateRequest;

import java.util.List;

public record DeliveryOrderCreateRequest(List<OrderLineItemCreateRequest> orderLineItems, String deliveryAddress) {
}
