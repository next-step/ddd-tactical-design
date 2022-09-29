package kitchenpos.products.dto;

import java.math.BigDecimal;

public class ProductDto {
    private String name;
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
