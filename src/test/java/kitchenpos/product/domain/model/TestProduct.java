package kitchenpos.product.domain.model;

import java.math.BigDecimal;

public class TestProduct {
    private String name;
    private BigDecimal price;

    public TestProduct(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public BigDecimal getInnerPrice() {
        return price;
    }

    public String getInnerName() {
        return name;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
