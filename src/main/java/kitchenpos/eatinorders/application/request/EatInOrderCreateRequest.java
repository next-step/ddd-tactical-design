package kitchenpos.eatinorders.application.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class EatInOrderCreateRequest {

    @NotEmpty
    private List<EatInOrderLineItemRequest> orderLineItems;

    @NotNull
    private UUID orderTableId;

    public EatInOrderCreateRequest() {
    }

    public List<EatInOrderLineItemRequest> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<EatInOrderLineItemRequest> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void setOrderTableId(UUID orderTableId) {
        this.orderTableId = orderTableId;
    }
}
