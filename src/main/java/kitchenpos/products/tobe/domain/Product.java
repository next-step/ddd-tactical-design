package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

public class Product {
    private ProductId id;
    private String name;
    private Price price;

    public Product(Long id, String name, BigDecimal price) {
        this.id = ProductId.fromNumber(id);
        this.name = name;
        this.price = Price.valueOf(price);
    }

}
