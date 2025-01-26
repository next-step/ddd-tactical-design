package kitchenpos.shared.event;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductPriceChangeEvent {
    private final UUID id;
    private final BigDecimal productPrice;

    public ProductPriceChangeEvent(UUID id, BigDecimal productPrice) {
        this.id = id;
        this.productPrice = productPrice;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getProductPrice() {
        return this.productPrice;
    }
}
