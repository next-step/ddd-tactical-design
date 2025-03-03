package kitchenpos.tobe.product.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @EmbeddedId
    private ProductId id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(ProductName name, ProductPrice price) {
        this.id = ProductId.newId();
        this.name = name;
        this.price = price;
    }

    public void changePrice(ProductPrice price) {
        this.price = price;
    }

    public ProductId getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }
}
