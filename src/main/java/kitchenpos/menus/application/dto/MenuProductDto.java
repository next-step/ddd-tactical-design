package kitchenpos.menus.application.dto;

import java.util.UUID;

public class MenuProductDto {
    private UUID productId;
    private int quantity;

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
