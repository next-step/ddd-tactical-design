package kitchenpos.menus.tobe.dto;

import java.util.UUID;

public class UpdateMenuStatusRequest {
    private UUID productId;

    protected UpdateMenuStatusRequest() {}

    public UpdateMenuStatusRequest(final UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
