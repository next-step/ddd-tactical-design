package kitchenpos.products.application.dto;

import java.math.BigDecimal;

public class ProductRequest {
    private BigDecimal price;
    private String name;

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
