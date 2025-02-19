package kitchenpos.product.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.product.tobe.Profanities;

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

    public Product() {}

    public Product(String name, long price, Profanities profanities) {
        this.id = UUID.randomUUID();
        this.name = new ProductName(name, profanities);
        this.price = new ProductPrice(price);
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

    public void setName(final ProductName productName) {
        this.name = productName;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void setPrice(final ProductPrice productPrice) {
        this.price = productPrice;
    }
}
