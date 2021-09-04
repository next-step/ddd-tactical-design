package kitchenpos.products.dto;

import java.math.BigDecimal;

public class ProductChangePriceRequest {

    private BigDecimal price;

    public ProductChangePriceRequest(final BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
