package kitchenpos.product.application.dto;

import java.math.BigDecimal;

public class CreateProductServiceRq {
    private String name;
    private BigDecimal price;

    public CreateProductServiceRq(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public CreateProductServiceRq() {
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
