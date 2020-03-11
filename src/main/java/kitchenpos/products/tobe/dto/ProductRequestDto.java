package kitchenpos.products.tobe.dto;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class ProductRequestDto {
    private String name;
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Product toEntity() {
        return new Product(name, price);
    }
}
