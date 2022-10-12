package kitchenpos.products.tobe.domain.events;

import java.util.UUID;

public class ProductPriceChangedEvent {

    private final UUID productId;
    private final Long changedPrice;

    public ProductPriceChangedEvent(UUID productId, Long changedPrice) {
        this.productId = productId;
        this.changedPrice = changedPrice;
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getChangedPrice() {
        return changedPrice;
    }
}
