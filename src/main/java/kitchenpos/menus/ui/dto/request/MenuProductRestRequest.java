package kitchenpos.menus.ui.dto.request;


import kitchenpos.menus.tobe.domain.menu.ProductId;

import java.util.UUID;

public class MenuProductRestRequest {

    final private UUID productId;
    final private long quantity;

    public MenuProductRestRequest(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProductRestRequest(ProductId productId, long quantity) {
        this.productId = productId.getValue();
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

}
