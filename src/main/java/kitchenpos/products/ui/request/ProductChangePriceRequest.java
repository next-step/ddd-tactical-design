package kitchenpos.products.ui.request;

import java.math.BigDecimal;

public class ProductChangePriceRequest {

    private BigDecimal price;

    protected ProductChangePriceRequest() {
    }

    public ProductChangePriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
