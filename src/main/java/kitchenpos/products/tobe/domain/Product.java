package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(String name, BigDecimal price) {
        this(new ProductName(name), new ProductPrice(price));
    }

    public Product(ProductName name, ProductPrice price) {
        this(UUID.randomUUID(), name, price);
    }

    public Product(UUID productId, ProductName name, ProductPrice price) {
        this.id = productId;
        this.name = name;
        this.price = price;
    }

    public void changePrice(ProductPrice newPrice) {
        this.price = newPrice;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }
}
