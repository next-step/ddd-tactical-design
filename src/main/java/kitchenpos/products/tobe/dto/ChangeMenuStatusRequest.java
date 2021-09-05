package kitchenpos.products.tobe.dto;

import java.util.UUID;

public class ChangeMenuStatusRequest {
    private final UUID productId;

    public ChangeMenuStatusRequest(final UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
