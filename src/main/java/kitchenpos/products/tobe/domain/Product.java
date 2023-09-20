package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName productName;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(ProductPrice price) {
        this.price = price;
    }

    public Product(ProductName productName, ProductPrice price) {
        this(null, productName, price);
    }

    public Product(UUID id, ProductName productName, ProductPrice price) {
        this.id = id;
        this.productName = productName;
        this.price = price;
    }

    public Product giveId() {
        return new Product(UUID.randomUUID(), productName, price);
    }

    public void changePrice(ProductPrice price) {
        this.price = price;
    }

    public BigDecimal getPriceValue() {
        return this.price.getValue();
    }

    public UUID getId() {
        return id;
    }

    public ProductName getProductName() {
        return productName;
    }

    public ProductPrice getPrice() {
        return price;
    }
}
