package kitchenpos.products.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityPolicy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private ProductDisplayedName name;

    @Column(name = "price", nullable = false)
    @Embedded
    private Price price;

    protected Product() {

    }

    public Product(UUID id, ProductDisplayedName name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static Product of(String name, BigDecimal price, ProfanityPolicy profanityPolicy) {
        return new Product(
                UUID.randomUUID(),
                new ProductDisplayedName(name, profanityPolicy),
                new Price(price)
        );
    }

    public void changePrice(Price price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public BigDecimal getPriceValue() {
        return price.getValue();
    }

    public Price getPrice() {
        return price;
    }

    public ProductDisplayedName getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
