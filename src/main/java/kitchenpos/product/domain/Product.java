package kitchenpos.product.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private ProductId id;

    private String name;

    private BigDecimal price;

    public Product(ProductId id, String name, BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }

        if (Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
