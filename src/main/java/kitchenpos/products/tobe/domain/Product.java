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

    public Product(final PurgomalumClient purgomalumClient, final DisplayedName displayedName, final BigDecimal price) {
        DisplayedName.validatePurgomalum(purgomalumClient, displayedName.getName());
        this.displayedName = displayedName;
        this.price = new Price(price);
    }

    public Product(final PurgomalumClient purgomalumClient, final String displayedName, final Long price) {
        this(purgomalumClient, new DisplayedName(displayedName), BigDecimal.valueOf(price));
    }

    public Product(final PurgomalumClient purgomalumClient, final String displayedName, final BigDecimal price) {
        this(purgomalumClient, new DisplayedName(displayedName), price);
    }

    public Product(final PurgomalumClient purgomalumClient, final DisplayedName displayedName, final Long price) {
        this(purgomalumClient, displayedName, BigDecimal.valueOf(price));
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
