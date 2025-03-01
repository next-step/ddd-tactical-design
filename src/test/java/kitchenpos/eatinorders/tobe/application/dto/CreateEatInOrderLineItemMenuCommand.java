package kitchenpos.eatinorders.tobe.application.dto;

import java.util.UUID;

@Deprecated
public record CreateEatInOrderLineItemMenuCommand(
        UUID menuId,
        String name,
        int price
) {
}
