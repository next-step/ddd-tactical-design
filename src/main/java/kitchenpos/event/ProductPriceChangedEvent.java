package kitchenpos.event;

import java.math.BigDecimal;

public class ProductPriceChangedEvent {

    private final BigDecimal price;

    public ProductPriceChangedEvent(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
