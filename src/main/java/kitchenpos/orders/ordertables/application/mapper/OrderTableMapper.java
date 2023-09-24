package kitchenpos.orders.ordertables.application.mapper;

import kitchenpos.orders.ordertables.application.dto.OrderTableResponse;
import kitchenpos.orders.ordertables.domain.OrderTable;

import java.util.List;
import java.util.stream.Collectors;

public class OrderTableMapper {


    public static OrderTableResponse toDto(OrderTable orderTable) {
        return new OrderTableResponse(
                orderTable.getIdValue(),
                orderTable.getNameValue(),
                orderTable.getNumberOfGuestValue(),
                orderTable.isOccupied()
        );
    }

    public static List<OrderTableResponse> toDtos(List<OrderTable> orderTables) {
        return orderTables.stream()
                .map(OrderTableMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
