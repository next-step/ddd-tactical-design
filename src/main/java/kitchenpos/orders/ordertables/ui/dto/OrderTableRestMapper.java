package kitchenpos.orders.ordertables.ui.dto;

import kitchenpos.orders.ordertables.application.dto.OrderTableRequest;
import kitchenpos.orders.ordertables.application.dto.OrderTableResponse;
import kitchenpos.orders.ordertables.ui.dto.request.OrderTableRestRequest;
import kitchenpos.orders.ordertables.ui.dto.response.OrderTableRestResponse;

import java.util.List;
import java.util.stream.Collectors;

public class OrderTableRestMapper {

    public static OrderTableRequest toDto(OrderTableRestRequest restRequest) {
        return new OrderTableRequest(
                restRequest.getName(),
                restRequest.getNumberOfGuest(),
                restRequest.isOccupied()
        );
    }

    public static OrderTableRestResponse toRestDto(OrderTableResponse response) {
        return new OrderTableRestResponse(
                response.getId(),
                response.getName(),
                response.getNumberOfGuest(),
                response.isOccupied()
        );
    }

    public static List<OrderTableRestResponse> toRestDtos(List<OrderTableResponse> responses) {
        return responses.stream()
                .map(OrderTableRestMapper::toRestDto)
                .collect(Collectors.toUnmodifiableList());
    }

}
