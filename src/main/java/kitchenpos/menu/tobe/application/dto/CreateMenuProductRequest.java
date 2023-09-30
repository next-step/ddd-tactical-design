package kitchenpos.menu.tobe.application.dto;

import java.util.UUID;
import kitchenpos.menu.tobe.domain.MenuProduct;

public class CreateMenuProductRequest {

    private final UUID productId;
    private final long quantity;


    public CreateMenuProductRequest(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public static CreateMenuProductRequest of(MenuProduct menuProduct) {
        return new CreateMenuProductRequest(menuProduct.getProductId(), menuProduct.getQuantity());
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}
