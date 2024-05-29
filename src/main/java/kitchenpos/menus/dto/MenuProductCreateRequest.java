package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.ProductClient;

import java.util.UUID;

public record MenuProductCreateRequest(
        UUID productId, long quantity
) {
    public MenuProduct toMenuProduct(ProductClient productClient) {
        return MenuProduct.from(this.productId, this.quantity, productClient);
    }
}
