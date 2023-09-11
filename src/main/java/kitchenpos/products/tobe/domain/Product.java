package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.infra.PurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private Price price;

    public Product() {
    }

    public Product(final String name, PurgomalumClient purgomalumClient, final BigDecimal price) {
        this.id = UUID.randomUUID();
        this.name = new DisplayedName(name, purgomalumClient);
        this.price = new Price(price);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public DisplayedName getName() {
        return name;
    }

    public void setName(final String name, PurgomalumClient purgomalumClient) {
        this.name = new DisplayedName(name, purgomalumClient);
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = new Price(price);
    }
}
