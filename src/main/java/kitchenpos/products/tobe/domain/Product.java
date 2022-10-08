package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

public class Product {
    private final BigDecimal price;
    private final String name;

    public Product(final BigDecimal price, final String name) {
        this.price = price;
        this.name = name;
    }
}
