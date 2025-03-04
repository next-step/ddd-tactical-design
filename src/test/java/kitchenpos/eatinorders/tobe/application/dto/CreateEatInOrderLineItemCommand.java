package kitchenpos.eatinorders.tobe.application.dto;

import java.util.UUID;

public record CreateEatInOrderLineItemCommand(
        UUID menuId,
        String name,
        int price,
        int quantity
) {
}
