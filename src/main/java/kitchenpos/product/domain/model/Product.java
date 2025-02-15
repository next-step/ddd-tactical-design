package kitchenpos.product.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private final UUID id;
    private final ProductName name;
    private ProductPrice price;

    public Product(final UUID id, final String name, final BigDecimal price) {
        this.id = id;
        this.name = ProductName.of(name);
        this.price = ProductPrice.of(price);
    }

    public Product(final UUID id, final ProductName name, final ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.value();
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public void changePrice(final BigDecimal price) {
        this.price = ProductPrice.of(price);
    }
}
