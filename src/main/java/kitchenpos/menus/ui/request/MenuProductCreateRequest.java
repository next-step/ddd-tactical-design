package kitchenpos.menus.ui.request;

import java.util.UUID;

public class MenuProductCreateRequest {

    private UUID productId;
    private long quantity;

    protected MenuProductCreateRequest() {
    }

    public MenuProductCreateRequest(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}
