package kitchenpos.menus.dto;

import java.util.UUID;

public class MenuProductRequest {
    private UUID productId;
    private Long quantity;

    public UUID getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
