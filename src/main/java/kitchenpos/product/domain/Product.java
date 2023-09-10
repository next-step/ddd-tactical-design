package kitchenpos.product.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private ProductId id;

    private String name;

    private BigDecimal price;

    private Product() {}

    public static Product of(final ProductId id, final String name, final BigDecimal price) {
        final Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        return product;
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

    public Product changePrice(final BigDecimal newPrice) {
        return Product.of(id, name, newPrice);
    }

    private void setId(ProductId id) {
        this.id = id;
    }

    private void setName(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    private void setPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }

        this.price = price;
    }
}
