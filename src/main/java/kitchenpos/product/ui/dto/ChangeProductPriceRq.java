package kitchenpos.product.ui.dto;

import java.math.BigDecimal;

public class ChangeProductPriceRq {
    private BigDecimal price;

    public ChangeProductPriceRq(BigDecimal price) {
        this.price = price;
    }

    public ChangeProductPriceRq() {
    }

    public BigDecimal getPrice() {
        return price;
    }
}
