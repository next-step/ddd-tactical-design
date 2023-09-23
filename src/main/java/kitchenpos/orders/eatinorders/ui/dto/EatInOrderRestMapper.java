package kitchenpos.orders.eatinorders.ui.dto;

import kitchenpos.orders.eatinorders.application.dto.EatInOrderLineItemRequest;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderLineItemResponse;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderRequest;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderResponse;
import kitchenpos.orders.eatinorders.ui.dto.request.EatInOrderLineItemRestRequest;
import kitchenpos.orders.eatinorders.ui.dto.request.EatInOrderRestRequest;
import kitchenpos.orders.eatinorders.ui.dto.response.EatInOrderLineItemRestResponse;
import kitchenpos.orders.eatinorders.ui.dto.response.EatInOrderRestResponse;

import java.util.List;
import java.util.stream.Collectors;

public class EatInOrderRestMapper {

    public static EatInOrderRequest toDto(EatInOrderRestRequest restRequest) {
        return new EatInOrderRequest(
                restRequest.getOrderTableId(),
                toDtos(restRequest.getOrderLineItems())
        );
    }

    public static List<EatInOrderLineItemRequest> toDtos(List<EatInOrderLineItemRestRequest> restRequests) {
        return restRequests.stream()
                .map(EatInOrderRestMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    private static EatInOrderLineItemRequest toDto(EatInOrderLineItemRestRequest restRequest) {
        return new EatInOrderLineItemRequest(
                restRequest.getMenuId(),
                restRequest.getQuantity(),
                restRequest.getOrderPrice()
        );
    }

    public static EatInOrderRestResponse toRestDto(EatInOrderResponse response) {
        return new EatInOrderRestResponse(
                response.getId(),
                response.getEatInOrderStatus().name(),
                response.getOrderDateTime(),
                response.getOrderTableId(),
                toItemRestDtos(response.getOrderLineItems())
        );
    }

    public static EatInOrderLineItemRestResponse toItemRestDto(EatInOrderLineItemResponse response) {
        return new EatInOrderLineItemRestResponse(
                response.getSeq(),
                response.getMenuId(),
                response.getQuantity(),
                response.getPrice()
        );
    }

    private static List<EatInOrderLineItemRestResponse> toItemRestDtos(List<EatInOrderLineItemResponse> responses) {
        return responses.stream()
                .map(EatInOrderRestMapper::toItemRestDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public static List<EatInOrderRestResponse> toRestDtos(List<EatInOrderResponse> responses) {
        return responses.stream()
                .map(EatInOrderRestMapper::toRestDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
