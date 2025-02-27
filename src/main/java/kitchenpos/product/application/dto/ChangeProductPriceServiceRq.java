package kitchenpos.product.application.dto;

import java.math.BigDecimal;

public class ChangeProductPriceServiceRq {
    private BigDecimal price;

    public ChangeProductPriceServiceRq(BigDecimal price) {
        this.price = price;
    }

    public ChangeProductPriceServiceRq() {
    }

    public BigDecimal getPrice() {
        return price;
    }
}
