package kitchenpos.takeoutorders.dto;

import kitchenpos.support.dto.OrderLineItemCreateRequest;

import java.util.List;

public record TakeoutOrderCreateRequest(List<OrderLineItemCreateRequest> orderLineItems) {
}
