package kitchenpos.eatinorders.tobe.application.dto;

public record CreateEatInOrderLineItemCommand(
        CreateEatInOrderLineItemMenuCommand eatInOrderLineItemMenuCommand,
        int quantity
) {

}
