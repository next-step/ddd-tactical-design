package kitchenpos.products.tobe.application.dto;

import java.math.BigDecimal;

public class ProductPriceUpdateRequest {
    private final BigDecimal price;

    public ProductPriceUpdateRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
