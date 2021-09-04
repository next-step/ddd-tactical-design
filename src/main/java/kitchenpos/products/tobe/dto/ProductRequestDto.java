package kitchenpos.products.tobe.dto;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.model.Price;
import kitchenpos.products.tobe.domain.model.Product;

public class ProductRequestDto {

    private String name;
    private BigDecimal price;

    protected ProductRequestDto() {
    }

    public ProductRequestDto(final BigDecimal price) {
        this.price = price;
    }

    public ProductRequestDto(final String name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product toEntity() {
        return new Product(name, price);
    }

    public Price toPrice() {
        return new Price(price);
    }

}
