package kitchenpos.menus.ui.dto;

import java.util.UUID;
import kitchenpos.menus.domain.tobe.MenuProduct;
import kitchenpos.menus.domain.tobe.MenuQuantity;
import kitchenpos.products.domain.tobe.Product;

public class MenuProductCreateRequest {

    private UUID productId;

    private MenuQuantity quantity;

    public MenuProductCreateRequest(UUID productId, MenuQuantity quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProduct to(Product product) {
        return new MenuProduct(product, quantity);
    }

    public UUID getProductId() {
        return productId;
    }

    public MenuQuantity getQuantity() {
        return quantity;
    }
}
