package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

public class Product {

    private Long id;

    private String name;

    private Price price;

    public Product(final String name, final BigDecimal price) {
        this.name = name;
        this.price = new Price(price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
