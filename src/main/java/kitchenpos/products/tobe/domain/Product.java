package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;

    @Embedded
    @Column(name = "name", nullable = false)
    private DisplayedName name;

    protected Product() {
    }

    private Product(UUID id, Price price, DisplayedName name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public static Product create(BigDecimal price, String name) {
        return new Product(UUID.randomUUID(), Price.of(price), DisplayedName.of(name));
    }

    public UUID getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public BigDecimal getPriceValue() {
        return price.getPrice();
    }

    public DisplayedName getName() {
        return name;
    }

    public void changePrice(BigDecimal price) {
        this.price = Price.of(price);
    }
}
