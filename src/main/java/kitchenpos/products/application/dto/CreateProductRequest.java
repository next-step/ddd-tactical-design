package kitchenpos.products.application.dto;

import java.math.BigDecimal;

public class CreateProductRequest {
    private BigDecimal price;
    private String name;

    public CreateProductRequest() {}

    public CreateProductRequest(BigDecimal price, String name) {
        this.price = price;
        this.name = name;
    }

    public static CreateProductRequest of(BigDecimal price, String name) {
        return new CreateProductRequest(price, name);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
