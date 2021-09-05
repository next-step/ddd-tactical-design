package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity(name = "ProductsProduct")
public class Product {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName displayedName;

    @Embedded
    private Price price;

    public Product() {
    }

    public Product(final PurgomalumClient purgomalumClient, final String name, final BigDecimal price) {
        DisplayedName.validateName(purgomalumClient, name);
        this.displayedName = new DisplayedName(name);
        this.price = new Price(price);
    }

    public Product(final PurgomalumClient purgomalumClient, final String name, final Long price) {
        this(purgomalumClient, name, BigDecimal.valueOf(price));
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getDisplayedName() {
        return this.displayedName;
    }

    public Price getPrice() {
        return price;
    }
}
