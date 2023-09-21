package kitchenpos.products.tobe.domain;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;

import javax.persistence.*;
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

    public Product(final DisplayedName name, final BigDecimal price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = new Price(price);
    }

    public void changePrice(final BigDecimal price) {
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
}
