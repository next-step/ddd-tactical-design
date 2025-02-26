package kitchenpos.product.ui.dto;

import java.math.BigDecimal;

public class CreateProductRq {
    private String name;
    private BigDecimal price;

    public CreateProductRq(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public CreateProductRq() {
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
