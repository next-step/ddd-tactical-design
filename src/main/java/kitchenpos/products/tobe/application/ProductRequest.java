package kitchenpos.products.tobe.application;

import java.math.BigDecimal;

public class ProductRequest {
    private String name;
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
