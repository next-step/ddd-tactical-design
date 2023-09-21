package kitchenpos.menus.application.menu.dto;

import kitchenpos.menus.domain.menu.MenuProduct;

import java.util.UUID;

public class MenuProductRequest {

    private final UUID productId;
    private final long quantity;

    public MenuProductRequest(final UUID productId, final long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public static MenuProduct toMenuProduct(final MenuProductRequest menuProductRequest) {
        return new MenuProduct(menuProductRequest.getProductId(), menuProductRequest.getQuantity());
    }
}
