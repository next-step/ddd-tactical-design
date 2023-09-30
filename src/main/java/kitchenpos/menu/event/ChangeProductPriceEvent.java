package kitchenpos.menu.event;

import java.math.BigDecimal;
import java.util.UUID;

public class ChangeProductPriceEvent {
    private final UUID productId;
    private final BigDecimal price;

    public ChangeProductPriceEvent(UUID productId, BigDecimal price) {
        this.productId = productId;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
