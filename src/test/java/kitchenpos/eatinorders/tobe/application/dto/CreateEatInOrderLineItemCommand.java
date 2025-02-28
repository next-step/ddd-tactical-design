package kitchenpos.eatinorders.tobe.application.dto;

public record CreateEatInOrderLineItemCommand(
        CreateEatInOrderLineItemMenuCommand command,
        int quantity
) {

}
