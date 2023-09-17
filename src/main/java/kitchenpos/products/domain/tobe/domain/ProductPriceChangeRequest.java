package kitchenpos.products.domain.tobe.domain;

import java.util.UUID;

public class ProductPriceChangeRequest {
    private final UUID productId;

    public ProductPriceChangeRequest(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }

}
