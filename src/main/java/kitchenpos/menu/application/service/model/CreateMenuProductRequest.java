package kitchenpos.menu.application.service.model;

import java.util.UUID;

public class CreateMenuProductRequest {
    private UUID productId;
    private long quantity;

    public CreateMenuProductRequest() {
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
