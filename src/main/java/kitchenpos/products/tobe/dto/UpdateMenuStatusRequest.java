package kitchenpos.products.tobe.dto;

import java.util.UUID;

public class UpdateMenuStatusRequest {
    private final UUID productId;

    public UpdateMenuStatusRequest(final UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
