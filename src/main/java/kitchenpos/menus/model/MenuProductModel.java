package kitchenpos.menus.model;

import java.util.UUID;

public class MenuProductModel {
    private Long seq;
    private Long quantity;
    private UUID productId;

    public MenuProductModel(Long seq, Long quantity, UUID productId) {
        this.seq = seq;
        this.quantity = quantity;
        this.productId = productId;
    }

    public Long seq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Long quantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public UUID productId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
