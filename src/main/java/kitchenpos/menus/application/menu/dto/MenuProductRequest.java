package kitchenpos.menus.application.menu.dto;

import kitchenpos.menus.domain.menu.MenuProduct;
import kitchenpos.menus.domain.menu.ProductPrice;

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

    public MenuProduct toMenuProduct(final ProductPrice productPrice) {
        return new MenuProduct(null, this.productId, this.quantity, productPrice);
    }
}
