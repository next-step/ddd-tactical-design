package kitchenpos.menus.application.dto;

import java.util.UUID;

public class MenuProductDto {
    private UUID productId;
    private int quantity;

    public MenuProductDto() {
    }

    public MenuProductDto(UUID productId, int quantity) {
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
