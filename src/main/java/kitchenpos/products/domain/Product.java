package kitchenpos.products.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
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

    public Product(UUID id, String name, Long price, PurgomalumClient purgomalumClient) {
        this(id, new DisplayedName(name, purgomalumClient), new Price(price));
    }

    public Product(UUID id, DisplayedName name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void changePrice(Price price) {
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }
}
