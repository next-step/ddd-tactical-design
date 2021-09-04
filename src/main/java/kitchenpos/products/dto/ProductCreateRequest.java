package kitchenpos.products.dto;

import kitchenpos.products.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductCreateRequest {

    private String name;
    private BigDecimal price;

    public ProductCreateRequest(final String name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product toEntity() {
        return new Product(UUID.randomUUID(), this.name, this.price);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}

