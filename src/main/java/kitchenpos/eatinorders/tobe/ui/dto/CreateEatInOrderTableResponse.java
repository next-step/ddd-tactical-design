package kitchenpos.eatinorders.tobe.ui.dto;

import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTable;

import java.util.UUID;

public record CreateEatInOrderTableResponse(UUID id, String name, int numberOfGuests, boolean occupied) {

    public static CreateEatInOrderTableResponse from(final EatInOrderTable eatInOrderTable) {
        return new CreateEatInOrderTableResponse(
                eatInOrderTable.getId(),
                eatInOrderTable.getName(),
                eatInOrderTable.getNumberOfGuests(),
                eatInOrderTable.getOccupied()
        );
    }
}
