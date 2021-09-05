package kitchenpos.products.tobe.dto;

import java.math.BigDecimal;

public class ChangeProductPriceRequest {
    private BigDecimal price;

    protected ChangeProductPriceRequest() {}

    public ChangeProductPriceRequest(final BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
