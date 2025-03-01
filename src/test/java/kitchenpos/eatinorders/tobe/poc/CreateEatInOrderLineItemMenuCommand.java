package kitchenpos.eatinorders.tobe.poc;

import java.util.UUID;

@Deprecated
public record CreateEatInOrderLineItemMenuCommand(
        UUID menuId,
        String name,
        int price
) {
}
