package kitchenpos.products.application;

import java.util.UUID;

public class ProductChangePriceEvent {
    private final UUID productId;

    public ProductChangePriceEvent(final UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
