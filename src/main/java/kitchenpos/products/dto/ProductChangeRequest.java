package kitchenpos.products.dto;

import java.math.BigDecimal;

public class ProductChangeRequest {
    private BigDecimal price;

    public ProductChangeRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
