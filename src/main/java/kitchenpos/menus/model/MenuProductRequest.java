package kitchenpos.menus.model;

import java.util.UUID;

public class MenuProductRequest {
    private UUID productId;
    private Long quantity;

    public MenuProductRequest(UUID productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID productId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Long quantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
