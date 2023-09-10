package kitchenpos.products.ui.dto;

import kitchenpos.products.tobe.domain.DisplayedNamePolicy;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductDisplayedName;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductChangePriceRequest {
    private final UUID id;
    private final String displayedName;
    private final BigDecimal price;

    public ProductChangePriceRequest(UUID id, String displayedName, BigDecimal price) {
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
}
