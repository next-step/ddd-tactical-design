package kitchenpos.support.event;

import java.math.BigDecimal;
import java.util.UUID;

public final class ProductPriceChangedEvent {
    private final UUID id;
    private final String name;
    private final BigDecimal price;

    public ProductPriceChangedEvent(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
