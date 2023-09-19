package kitchenpos.menus.application.dto.request;

import kitchenpos.menus.domain.MenuProduct;

import java.util.UUID;

public class MenuProductCreateRequest {
    private UUID productId;
    private Long quantity;

    public MenuProductCreateRequest() {
    }

    public MenuProductCreateRequest(UUID productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public MenuProduct toMenuProduct() {
        return new MenuProduct(this.quantity, this.productId);
    }
}
