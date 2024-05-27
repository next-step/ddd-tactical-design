package kitchenpos.domain.product.tobe.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(String name, BigDecimal price) {
        this(UUID.randomUUID(), name, price);
    }

    public Product(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }

    public String name() {
        return name.getName();
    }

    public BigDecimal price() {
        return price.getPrice();
    }
}
