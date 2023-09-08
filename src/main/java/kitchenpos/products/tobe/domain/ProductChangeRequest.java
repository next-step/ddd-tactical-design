package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

public class ProductChangeRequest {
    private BigDecimal price;

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
