package kitchenpos.menus.domain.vo;

import java.util.UUID;

public class MenuProductVo {
    private UUID productId;
    private int quantity;

    public MenuProductVo() {
    }

    public MenuProductVo(UUID productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
