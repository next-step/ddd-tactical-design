package kitchenpos.products.tobe.application.dto;

import kitchenpos.menus.application.dto.MenuEvent;

import java.util.UUID;

public class ProductEvent extends MenuEvent {

    private final UUID productId;

    public ProductEvent(final UUID productId) {
        this.productId = productId;
    }

    @Override
    public UUID getProductId() {
        return productId;
    }
}
