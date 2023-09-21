package kitchenpos.products.domain;

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

    protected Product() {
    }

    public Product(final String name, PurgomalumClient purgomalumClient, final BigDecimal price) {
        this.id = UUID.randomUUID();
        this.name = new DisplayedName(name, purgomalumClient);
        this.price = new Price(price);
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public void changePrice(final BigDecimal price) {
        this.price = new Price(price);
    }

    public Price multiplyPrice(final long quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
