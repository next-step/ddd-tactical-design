package kitchenpos.products.domain;

import java.util.UUID;

public class ProductPriceChangeEvent {

    private final UUID productId;

    public ProductPriceChangeEvent(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
