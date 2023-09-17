package kitchenpos.menus.dto;

import java.util.UUID;

public class CreateMenuProductRequest {

    private Long seq;
    private long quantity;
    private UUID productId;

    public CreateMenuProductRequest(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getSeq() {
        return seq;
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }

}
