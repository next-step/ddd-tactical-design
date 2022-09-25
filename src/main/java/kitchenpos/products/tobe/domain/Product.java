package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.InvalidProductNameException;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private Price price;

    protected Product() {
    }

    private Product(final UUID id, final String name, final Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static Product create(final String name, final Long price, final PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new InvalidProductNameException();
        }
        return new Product(UUID.randomUUID(), name, Price.valueOf(price));
    }

    public void changePrice(final Long price) {
        this.price = Price.valueOf(price);
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Price price() {
        return price;
    }
}
