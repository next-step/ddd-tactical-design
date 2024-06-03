package kitchenpos.products.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.products.infra.tobe.Profanities;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Product는 식별자와 DisplayedName, 가격을 가진다.
 * DisplayedName에는 Profanity가 포함될 수 없다.
 */
@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;
    @Embedded
    @Column(name = "name", nullable = false)
    private DisplayedName dispayedName;
    @Embedded
    private Price price;

    protected Product() {
    }

    private Product(final DisplayedName name, final Price price) {
        this.id = UUID.randomUUID();
        this.dispayedName = name;
        this.price = price;
    }

    public static final Product createProduct(final String name, final BigDecimal price, final Profanities profanities) {
        DisplayedName displayedName = DisplayedName.createDisplayedName(name, profanities);
        Price displayedPrice = Price.createPrice(price);

        return new Product(displayedName, displayedPrice);
    }

    public Product changePrice(final BigDecimal price) {
        this.price = this.price.changePrice(Price.createPrice(price));

        return this;
    }

    public DisplayedName getDispayedName() {
        return dispayedName;
    }

    public Price getPrice() {
        return price;
    }

    public UUID getId() {
        return id;
    }
}
