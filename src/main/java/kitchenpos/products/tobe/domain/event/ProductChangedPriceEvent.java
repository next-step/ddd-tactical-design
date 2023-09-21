package kitchenpos.products.tobe.domain.event;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductChangedPriceEvent {
    private final UUID productId;
    private final BigDecimal changedPrice;

    public ProductChangedPriceEvent(final UUID productId, final BigDecimal changedPrice) {
        this.productId = productId;
        this.changedPrice = changedPrice;
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getChangedPrice() {
        return changedPrice;
    }
}
