package kitchenpos.menus.application.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class MenuCreateProductRequest {
    @NotNull
    private final UUID productId;
    @NotNull
    private final Long quantity;

    public MenuCreateProductRequest(UUID productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
