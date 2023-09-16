package kitchenpos.menus.application.dto;

import kitchenpos.menus.tobe.domain.MenuProduct;

import java.util.UUID;

public class MenuProductResponse {
    private Long seq;
    private UUID productId;
    private long quantity;

    public MenuProductResponse(final Long seq, final UUID productId, final Long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static MenuProductResponse from(MenuProduct menuProduct) {
        return new MenuProductResponse(
                menuProduct.getSeq(),
                menuProduct.getProductId(),
                menuProduct.getQuantity()
        );
    }

    public Long getSeq() {
        return seq;
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
