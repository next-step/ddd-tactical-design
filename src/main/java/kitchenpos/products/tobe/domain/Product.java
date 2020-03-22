package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

public class Product {
    private ProductId id;
    private String name;
    private Price price;

    public Product(String name, BigDecimal price) {
        this.id = ProductId.newProduct();
        this.name = name;
        this.price = Price.valueOf(price);
    }

    protected ProductId Id() { return this.id; }

    public Long getId() { return this.id.getValue();  }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() { return this.price.getValue(); }

}
