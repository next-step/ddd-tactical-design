package kitchenpos.products.domain.dto;

import java.math.BigDecimal;

public class ProductPriceRequest {
    private BigDecimal price;

    protected ProductPriceRequest() {
    }

    public ProductPriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
