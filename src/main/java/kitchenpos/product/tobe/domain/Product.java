package kitchenpos.product.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.common.tobe.Profanities;

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

    protected Product() {}

    public Product(String name, long price, Profanities profanities) {
        this.id = UUID.randomUUID();
        this.name = new ProductName(name, profanities);
        this.price = new ProductPrice(price);
    }

    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }


    public ProductPrice getProductPrice() {
        return price;
    }
    public Long getPrice() {
        return price.getPrice();
    }

    public void updatePrice(final ProductPrice productPrice) {
        this.price = productPrice;
    }
}
