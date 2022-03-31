package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Entity
public class Product {

    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(final Name name, final Price price) {
        this(randomUUID(), name, price);
    }

    public Product(final UUID id, final Name name, final Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void changePrice(final Price price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(final Price price) {
        this.price = price;
    }

}
