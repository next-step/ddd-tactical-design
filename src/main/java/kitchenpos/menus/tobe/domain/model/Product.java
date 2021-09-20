package kitchenpos.menus.tobe.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private UUID id;
    private String displayedName;
    private BigDecimal price;

    public Product(UUID id, String displayedName, BigDecimal price) {
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal calculatePriceWithQuantity(long quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
