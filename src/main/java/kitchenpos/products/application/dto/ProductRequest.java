package kitchenpos.products.application.dto;

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

    public ProductRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public ProductRequest() {
    }
}
