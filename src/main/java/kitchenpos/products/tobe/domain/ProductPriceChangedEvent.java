package kitchenpos.products.tobe.domain;

import java.util.UUID;

public class ProductPriceChangedEvent {
    private final UUID productId;

    public ProductPriceChangedEvent(final UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
