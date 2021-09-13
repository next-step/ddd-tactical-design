package kitchenpos.menus.tobe.dto;

import java.util.UUID;
import kitchenpos.menus.tobe.domain.model.MenuProduct;

public class MenuProductRequest {
    private UUID productId;
    private long quantity;

    protected MenuProductRequest() {
    }

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

    public MenuProduct toEntity() {
        return null;
    }
}
