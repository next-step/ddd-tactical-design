package kitchenpos.menus.application.dto;

import java.util.UUID;

public class MenuProductCreateRequest {

    private final UUID productId;
    private final long quantity;

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
