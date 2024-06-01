package kitchenpos.domain.product.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.domain.support.BlackWordClient;
import kitchenpos.domain.support.Name;
import kitchenpos.domain.support.Price;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(String name, BigDecimal price) {
        this(UUID.randomUUID(), name, price);
    }

    public Product(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = new Name(name);
        this.price = new Price(price);
    }

    public Product(UUID id, String name, BigDecimal price, BlackWordClient blackWordClient) {
        this.id = id;
        this.name = new Name(name, blackWordClient);
        this.price = new Price(price);
    }

    public void changePrice(BigDecimal price) {
        this.price = new Price(price);
    }

    public String name() {
        return name.getName();
    }

    public BigDecimal price() {
        return price.getPrice();
    }
}
