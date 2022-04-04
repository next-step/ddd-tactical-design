package kitchenpos.common.domain;

import java.util.UUID;

public class ProductChangedEvent {
    private final UUID productId;

    public ProductChangedEvent(final UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
