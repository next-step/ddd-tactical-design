package kitchenpos.products.application;

import java.util.UUID;

public class ProductEvent {
    private final UUID productId;

    public ProductEvent(final UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
