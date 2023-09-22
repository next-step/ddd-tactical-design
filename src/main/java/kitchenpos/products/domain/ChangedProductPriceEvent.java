package kitchenpos.products.domain;

import java.util.UUID;

public class ChangedProductPriceEvent {
    private final UUID productId;
    private final long price;

    public ChangedProductPriceEvent(UUID productId, Price price) {
        this.productId = productId;
        this.price = price.longValue();
    }

    public UUID getProductId() {
        return productId;
    }

    public long getPrice() {
        return price;
    }
}
