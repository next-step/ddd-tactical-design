package kitchenpos.products.ui.dto;

import java.math.BigDecimal;

public class ProductChangePriceRequest {
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
