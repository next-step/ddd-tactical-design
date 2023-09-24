package kitchenpos.menus.application.dto;

import kitchenpos.menus.tobe.domain.menu.ProductId;

import java.util.UUID;

public class MenuProductResponse {

    final private long seq;
    final private long quantity;
    final private UUID productId;

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
