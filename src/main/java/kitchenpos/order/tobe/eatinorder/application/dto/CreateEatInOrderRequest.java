package kitchenpos.order.tobe.eatinorder.application.dto;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateEatInOrderRequest {

    @NotNull
    private UUID orderTableId;

    @NotEmpty
    @NotNull
    private List<EatInOrderLineItemDto> orderLineItems;

    public CreateEatInOrderRequest(UUID orderTableId, List<EatInOrderLineItemDto> orderLineItems) {
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<EatInOrderLineItemDto> getOrderLineItems() {
        return orderLineItems;
    }
}
