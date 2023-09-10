package kitchenpos.menus.dto;

import java.util.UUID;

public class MenuProductRequest {

    private UUID productId;
    private long quantity;

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
