package kitchenpos.menus.dto;


import kitchenpos.menus.tobe.domain.menu.ProductId;

import java.util.UUID;

public class MenuProductRequest {

    private UUID productId;
    private long quantity;

    public MenuProductRequest() {
    }

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

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
