package kitchenpos.products.tobe.dto;

import java.math.BigDecimal;

public class ChangePriceRequest {
    private BigDecimal price;

    protected ChangePriceRequest() {}

    public ChangePriceRequest(final BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
