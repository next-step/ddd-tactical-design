package kitchenpos.products.dto;

import java.math.BigDecimal;
import kitchenpos.products.domain.Product;

public class ProductCreateDto {

    private String name;
    private BigDecimal price;

    ProductCreateDto(final String name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product toProduct() {
        return new Product(name, price);
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
}
