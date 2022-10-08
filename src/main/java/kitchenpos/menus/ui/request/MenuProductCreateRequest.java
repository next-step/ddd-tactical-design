package kitchenpos.menus.ui.request;

import java.util.UUID;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuProductQuantity;

public class MenuProductCreateRequest {

    private UUID productId;
    private long quantity;

    protected MenuProductCreateRequest() {
    }

    public MenuProductCreateRequest(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProduct toMenuProduct(Menu menu) {
        return null;
//        return new MenuProduct(menu, productId, new MenuProductQuantity(quantity));
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}
