package kitchenpos.products.dto;

import java.math.BigDecimal;

public final class ProductChangePriceRequest {
    private BigDecimal price;

    public ProductChangePriceRequest() {
    }

    public ProductChangePriceRequest(BigDecimal price) {
        this.price = price;
    }


    public BigDecimal getPrice() {
        return price;
    }
}
