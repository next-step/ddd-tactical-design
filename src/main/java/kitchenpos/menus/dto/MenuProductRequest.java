package kitchenpos.menus.dto;

import java.util.UUID;

public class MenuProductRequest {
    private final UUID productId;
    private final long quantity;

    public MenuProductRequest(UUID productId, long quantity) {
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
