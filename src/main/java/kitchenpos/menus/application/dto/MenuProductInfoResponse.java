package kitchenpos.menus.application.dto;

import java.util.UUID;

public class MenuProductInfoResponse {
    private UUID productId;
    private Long quantity;

    public MenuProductInfoResponse(UUID productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
