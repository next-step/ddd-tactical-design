package kitchenpos.products.tobe.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Product {
    @EmbeddedId
    private ProductId id;

    @Embedded
    private ProductName name;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(ProductId id, ProductName name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductId getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
