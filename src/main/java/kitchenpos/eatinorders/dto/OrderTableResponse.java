package kitchenpos.eatinorders.dto;

import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;

import java.util.UUID;

public record OrderTableResponse(UUID id, String name, int numberOfGuests, boolean occupied) {
    public static OrderTableResponse from(OrderTable orderTable) {
        return new OrderTableResponse(
                orderTable.getId(),
                orderTable.name(),
                orderTable.getNumberOfGuests(),
                orderTable.isOccupied()
        );
    }
}
