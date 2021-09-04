package kitchenpos.menus.dto;

import java.util.UUID;

public class ChangeMenuStatusRequest {
    private UUID productId;

    protected ChangeMenuStatusRequest() {}

    public ChangeMenuStatusRequest(final UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
