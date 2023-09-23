package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.menu.ProductId;

import java.util.UUID;

public class MenuProductResponse {

    private long seq;
    private long quantity;
    private UUID productId;

    public MenuProductResponse() {
    }

    public MenuProductResponse(long seq, ProductId productId, long quantity) {
        this.seq = seq;
        this.productId = productId.getValue();
        this.quantity = quantity;
    }

    public long getSeq() {
        return seq;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

}
