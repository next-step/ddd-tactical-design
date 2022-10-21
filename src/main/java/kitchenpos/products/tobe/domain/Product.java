package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

    protected Product() {}

    public Product(UUID id, Name name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return this.id;
    }

    public void changePrice(BigDecimal price) {
        this.price = new Price(price);
    }

    public Price getPrice() {
        return this.price;
    }

    public BigDecimal price() {
        return this.price.price();
    }

}
