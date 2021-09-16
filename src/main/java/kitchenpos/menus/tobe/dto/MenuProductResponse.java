package kitchenpos.menus.tobe.dto;

import kitchenpos.menus.tobe.domain.menuproducts.MenuProduct;
import kitchenpos.products.tobe.dto.ProductResponse;

import java.util.UUID;

public class MenuProductResponse {
    private final Long seq;
    private final ProductResponse product;
    private final long quantity;
    private final UUID productId;

    private MenuProductResponse(final Long seq, final ProductResponse product, final long quantity, final UUID productId) {
        this.seq = seq;
        this.product = product;
        this.quantity = quantity;
        this.productId = productId;
    }

    public Long getSeq() {
        return seq;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public static MenuProductResponse from(final MenuProduct menuProduct) {
        return new MenuProductResponse(
                menuProduct.getSeq(),
                ProductResponse.from(menuProduct.getProduct()),
                menuProduct.getQuantity(),
                menuProduct.getProductId()
        );
    }
}
