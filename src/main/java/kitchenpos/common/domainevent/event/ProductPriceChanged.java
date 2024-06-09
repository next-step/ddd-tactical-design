package kitchenpos.common.domainevent.event;

import java.util.UUID;

public class ProductPriceChanged {
    private final UUID productId;

    public ProductPriceChanged(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
