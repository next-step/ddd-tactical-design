package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductDisplayedName productDisplayedName;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(ProductPrice price) {
        this.price = price;
    }

    public Product(ProductDisplayedName productDisplayedName, ProductPrice price) {
        this(null, productDisplayedName, price);
    }

    public Product(UUID id, ProductDisplayedName productDisplayedName, ProductPrice price) {
        this.id = id;
        this.productDisplayedName = productDisplayedName;
        this.price = price;
    }

    public Product giveId() {
        return new Product(UUID.randomUUID(), productDisplayedName, price);
    }

    public void changePrice(ProductPrice price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public ProductDisplayedName getDisplayedName() {
        return productDisplayedName;
    }

    public ProductPrice getPrice() {
        return price;
    }
}
