package kitchenpos.products.tobe.ui.dto;

import java.util.UUID;

public class ProductChangePriceEvent {

    private UUID productId;

    public ProductChangePriceEvent(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
