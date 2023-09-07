package kitchenpos.products.dto;

import java.math.BigDecimal;

public final class ProductChangePriceRequest {
    private final BigDecimal price;

    public ProductChangePriceRequest(BigDecimal price) {
        this.price = price;
    }


    public BigDecimal getPrice() {
        return price;
    }
}
