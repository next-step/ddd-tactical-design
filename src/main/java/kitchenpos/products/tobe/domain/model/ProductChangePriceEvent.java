package kitchenpos.products.tobe.domain.model;

import java.util.UUID;

public class ProductChangePriceEvent {

    private UUID productId;

    public ProductChangePriceEvent(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
