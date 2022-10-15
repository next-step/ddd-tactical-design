package kitchenpos.menus.tobe.dto.menu;

import java.util.UUID;

public class ProductRequest {

    private UUID productId;

    public ProductRequest(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return this.productId;
    }
}
