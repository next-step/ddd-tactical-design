package kitchenpos.eatinorders.ui.request;

import java.util.UUID;

public class EatInOrderLineItemCreateRequest {

    private UUID menuId;
    private long quantity;

    protected EatInOrderLineItemCreateRequest() {
    }

    public EatInOrderLineItemCreateRequest(UUID menuId, long quantity) {
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public long getQuantity() {
        return quantity;
    }
}
