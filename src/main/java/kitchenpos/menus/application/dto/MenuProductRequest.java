package kitchenpos.menus.application.dto;


import kitchenpos.menus.tobe.domain.menu.ProductId;

import java.util.UUID;

public class MenuProductRequest {

    final private UUID productId;
    final private long quantity;

    public MenuProductRequest(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProductRequest(ProductId productId, long quantity) {
        this.productId = productId.getValue();
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

}
