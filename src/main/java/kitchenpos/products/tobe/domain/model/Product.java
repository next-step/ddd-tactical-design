package kitchenpos.products.tobe.domain.model;

import java.math.BigDecimal;
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

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private DisplayedName name;

    @Column(name = "price", nullable = false)
    @Embedded
    private Price price;

    protected Product() {

    }

    public Product(final String name, final int price) {
        this(name, BigDecimal.valueOf(price));
    }

    public Product(final String name, final Long price) {
        this(name, BigDecimal.valueOf(price));
    }

    public Product(final String name, final BigDecimal price) {
        this.id = UUID.randomUUID();
        this.name = new DisplayedName(name);
        this.price = new Price(price);
    }

    public UUID getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public void validateProfanity(final PurgomalumClient purgomalumClient) {
        name.validateProfanity(purgomalumClient);
    }

    public Product changePrice(final Price price) {
        this.price = price;
        return this;
    }

}
