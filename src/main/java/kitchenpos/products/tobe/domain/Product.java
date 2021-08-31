package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName displayedName;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(final DisplayedName displayedName, final Price price) {
        this.id = UUID.randomUUID();
        this.displayedName = displayedName;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String displayName() {
        return displayedName.display();
    }

    public BigDecimal offerPrice() {
        return price.offer();
    }

    public void changePrice(final Price price) {
        this.price = price;
    }

    public void changePrice(final BigDecimal price) {
        changePrice(new Price(price));
    }

    public boolean equals(final UUID id) {
        if (Objects.isNull(id)) {
            return false;
        }

        return this.id.equals(id);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }

        final Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
