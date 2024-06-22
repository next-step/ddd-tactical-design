package kitchenpos.menus.application.dto;

import java.util.UUID;
import kitchenpos.menus.domain.tobe.MenuProduct;
import kitchenpos.menus.domain.tobe.ProductQuantity;
import kitchenpos.products.domain.tobe.Product;

public class MenuProductCreateRequest {

    private final UUID productId;

    private final ProductQuantity quantity;

    public MenuProductCreateRequest(UUID productId, ProductQuantity quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProduct to(Product product) {
        return new MenuProduct(product, quantity);
    }

    public UUID getProductId() {
        return productId;
    }

    public ProductQuantity getQuantity() {
        return quantity;
    }
}
