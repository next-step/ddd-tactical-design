package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    protected Product(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static Product from(ProductName name, ProductPrice price) {
        return new Product(UUID.randomUUID(), name, price);
    }

    public void changePrice(ProductPrice price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.name();
    }

    public BigDecimal getPrice() {
        return price.price();
    }
}
