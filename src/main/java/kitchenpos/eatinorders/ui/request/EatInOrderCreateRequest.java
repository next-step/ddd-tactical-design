package kitchenpos.eatinorders.ui.request;

import java.util.List;
import java.util.UUID;

public class EatInOrderCreateRequest {

    private UUID eatInOrderTableId;
    private List<EatInOrderLineItemCreateRequest> eatInOrderLineItems;

    protected EatInOrderCreateRequest() {
    }

    public EatInOrderCreateRequest(
        UUID eatInOrderTableId,
        List<EatInOrderLineItemCreateRequest> eatInOrderLineItems
    ) {
        this.eatInOrderTableId = eatInOrderTableId;
        this.eatInOrderLineItems = eatInOrderLineItems;
    }

    public UUID getEatInOrderTableId() {
        return eatInOrderTableId;
    }

    public List<EatInOrderLineItemCreateRequest> getEatInOrderLineItems() {
        return eatInOrderLineItems;
    }
}
