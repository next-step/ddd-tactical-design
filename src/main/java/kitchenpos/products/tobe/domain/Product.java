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
    private Name name;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(final Price price) {
        this.price = price;
    }

    public Product(final Name name, final Price price) {
        this(null, name, price);
    }

    public Product(final UUID id, final Name name, final Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static Product from(String name, BigDecimal price, ProductNamePolicy productNamePolicy) {
        return new Product(Name.from(name, productNamePolicy), Price.from(price));
    }

    public static Product from(UUID id, String name, BigDecimal price, ProductNamePolicy productNamePolicy) {
        return new Product(id, Name.from(name, productNamePolicy), Price.from(price));
    }

    public Product giveId() {
        return new Product(UUID.randomUUID(), name, price);
    }

    public void changePrice(final BigDecimal price) {
        this.price = Price.from(price);
    }

    public BigDecimal getPriceValue() {
        return this.price.getValue();
    }

    public String getNameValue() {
        return this.name.getValue();
    }

    public UUID getId() {
        return id;
    }

    public Name getProductName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
