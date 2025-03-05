package kitchenpos.products.tobe.domain.event;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductPriceChangedEvent {
    private final UUID productId;
    private final BigDecimal newPrice;

    public ProductPriceChangedEvent(final UUID productId, final BigDecimal newPrice) {
        this.productId = productId;
        this.newPrice = newPrice;
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }
}
