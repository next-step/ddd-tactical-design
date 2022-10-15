package kitchenpos.products.tobe.domain;

import java.util.UUID;

public class ChangeProductPriceEvent {
    private UUID productId;

    public ChangeProductPriceEvent(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
