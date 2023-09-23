package kitchenpos.orders.ordertables.application.mapper;

import kitchenpos.orders.ordertables.domain.OrderTable;
import kitchenpos.orders.ordertables.dto.OrderTableResponse;

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
