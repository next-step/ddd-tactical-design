package kitchenpos.products.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.products.infra.PurgomalumClient;

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

    public Product(final String name, final BigDecimal price) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }

        this.id = UUID.randomUUID();
        this.name = name;
        this.price = new Price(price);
    }

    public void verifySlang(PurgomalumClient purgomalumClient) {
        if (purgomalumClient.containsProfanity(this.name)) {
            throw new IllegalArgumentException();
        }
    }

    public void changePrice(final BigDecimal price) {
        this.price = new Price(price);
    }

    public UUID getId() {
        return this.id;
    }

    public Price getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }
}
