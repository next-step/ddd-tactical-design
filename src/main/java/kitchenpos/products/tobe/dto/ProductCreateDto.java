package kitchenpos.products.tobe.dto;

import java.math.BigDecimal;

public class ProductCreateDto {

    private String name;

    private BigDecimal price;

    public ProductCreateDto(String name, BigDecimal price) {
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
