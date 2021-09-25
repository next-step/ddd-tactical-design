package kitchenpos.menus.tobe.ui.dto;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.Quantity;

import java.util.UUID;

public class MenuProductRequest {
    private final UUID productId;
    private final long quantity;

    public MenuProductRequest(final UUID productId, final long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProduct toEntity() {
        return new MenuProduct(productId, new Quantity(quantity));
    }
}
