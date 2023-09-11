package kitchenpos.menus.dto;

import java.util.UUID;

public final class MenuProductElement {
    private final UUID productId;
    private final Long quantity;

    public MenuProductElement(UUID productId, Long quantity) {
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
