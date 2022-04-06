package kitchenpos.products.ui.dto;

import java.math.BigDecimal;

public class CreateProductRequest {
    private final String name;
    private BigDecimal price;

    public CreateProductRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
