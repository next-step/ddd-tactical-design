package kitchenpos.menus.ui.dto;

import kitchenpos.menus.tobe.domain.MenuProduct;

import java.util.UUID;

public class MenuProductsRequest {

    private UUID productId;
    private long quantity;

    public MenuProductsRequest(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public MenuProduct toEntity() {
        return new MenuProduct(productId, quantity);
    }
}
