package kitchenpos.menus.tobe.domain.menu;

import java.util.UUID;

public class MenuProductMaterial {
    private final UUID productId;
    private final long quantity;

    public MenuProductMaterial(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return this.productId;
    }

    public long getQuantity() {
        return this.quantity;
    }
}
