package kitchenpos.products.domain.tobe.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import kitchenpos.common.domain.ProductId;

@Entity
public class Product {

    @EmbeddedId
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private ProductId id;

    @Embedded
    @Column(name = "displayed_name", nullable = false)
    private DisplayedName displayedName;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;

    protected Product() {
    }

    public Product(final ProductId id, final DisplayedName displayedName, final Price price) {
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
    }

    public ProductId getId() {
        return this.id;
    }

    public DisplayedName getDisplayedName() {
        return this.displayedName;
    }

    public Price getPrice() {
        return this.price;
    }

    public void changePrice(final Price price) {
        this.price = price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
