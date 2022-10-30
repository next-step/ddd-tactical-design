package kitchenpos.products.tobe.domain.event;

import java.util.UUID;

public class ProductPriceChangedEvent {
    private final UUID productId;

    public ProductPriceChangedEvent(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
