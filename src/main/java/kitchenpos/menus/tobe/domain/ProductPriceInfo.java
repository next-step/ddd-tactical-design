package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductPriceInfo {
    private UUID id;
    private BigDecimal price;

    public ProductPriceInfo(UUID id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
