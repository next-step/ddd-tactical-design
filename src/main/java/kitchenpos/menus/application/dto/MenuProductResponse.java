package kitchenpos.menus.application.dto;

import java.util.UUID;

import kitchenpos.menus.domain.MenuProduct;

public class MenuProductResponse {
    private UUID productId;
    private Long quantity;
    private Long price;

    public MenuProductResponse(final UUID productId, final Long quantity, final Long price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public MenuProductResponse(final MenuProduct menuProduct) {
        this(menuProduct.getProductId(), menuProduct.getQuantity().longValue(), menuProduct.getPrice().longValue());
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getPrice() {
        return price;
    }
}
