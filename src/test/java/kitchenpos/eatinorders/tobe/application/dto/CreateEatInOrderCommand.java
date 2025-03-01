package kitchenpos.eatinorders.tobe.application.dto;

import java.util.List;
import java.util.UUID;

public record CreateEatInOrderCommand(
        UUID orderTableId,
        List<CreateEatInOrderLineItemCommand> lineItems
) {
    public List<UUID> menuIds() {
        return lineItems.stream()
                .map(CreateEatInOrderLineItemCommand::eatInOrderLineItemMenuCommand)
                .map(CreateEatInOrderLineItemMenuCommand::menuId)
                .toList();
    }

    public UUID orderTableId() {
        return orderTableId;
    }
}

