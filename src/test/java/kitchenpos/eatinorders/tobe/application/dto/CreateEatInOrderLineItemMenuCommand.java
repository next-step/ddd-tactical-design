package kitchenpos.eatinorders.tobe.application.dto;

import java.util.UUID;

public record CreateEatInOrderLineItemMenuCommand(
        UUID menuId,
        String name,
        int price
) {
}
