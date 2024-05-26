package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity(name = "tobeProduct")
public class Product {

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private Price price;

    protected Product() {
    }

    private Product(final DisplayedName name, final Price price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
    }

    public static Product create(final String name, final BigDecimal price,
        final ProfanityCheckProvider profanityCheckProvider) {
        return new Product(
            new DisplayedName(name, profanityCheckProvider),
            new Price(price)
        );
    }

    public UUID getId() {
        return this.id;
    }

    public Price getPrice() {
        return this.price;
    }

    public DisplayedName getName() {
        return this.name;
    }

    public void changePrice(final BigDecimal price) {
        this.price = new Price(price);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product that = (Product) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
