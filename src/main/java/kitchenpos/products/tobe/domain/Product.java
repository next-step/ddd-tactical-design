package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private Price price;

    public Product(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = Price.of(price);
    }

}
