package kitchenpos.product.application.service.model;

import java.math.BigDecimal;

public class ChangeProductPriceRequest {
    private BigDecimal price;

    public ChangeProductPriceRequest() {
    }

    public ChangeProductPriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
