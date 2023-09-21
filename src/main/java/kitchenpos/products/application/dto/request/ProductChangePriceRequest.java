package kitchenpos.products.application.dto.request;

import java.math.BigDecimal;

public class ProductChangePriceRequest {
    private BigDecimal price;

    private ProductChangePriceRequest() {
    }

    public ProductChangePriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
