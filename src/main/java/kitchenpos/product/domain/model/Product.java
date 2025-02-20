package kitchenpos.product.domain.model;

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

    public Product(ProductName name, ProductPrice price, UUID id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public Product(ProductName name, ProductPrice price) {
        this(name, price, UUID.randomUUID());
    }

    protected Product() {

    }

    public Product(String name, BigDecimal price, UUID id) {
        this(new ProductName(name), new ProductPrice(price), id);
    }

    public Product(String name, BigDecimal price) {
        this(name, price, UUID.randomUUID());
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getInnerName() {
        return name.getValue();
    }

    public ProductName getName() {
        return name;
    }

//    public void setName(final String name) {
//        this.name = name;
//    }

    public BigDecimal getInnerPrice() {
        return price.getValue();
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }

//    public void setPrice(final BigDecimal price) {
//        this.price = price;
//    }
}
