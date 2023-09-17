package kitchenpos.menus.application.dto;

import java.util.UUID;

public class MenuProductCreateRequest {
    private UUID productId;
    private Long quantity;

    public MenuProductCreateRequest() {}

    private MenuProductCreateRequest(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public static MenuProductCreateRequest create(UUID productId, long quantity) {
        return new MenuProductCreateRequest(productId, quantity);
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
