package kitchenpos.products.tobe.domain;

import kitchenpos.common.domain.vo.DisplayedName;
import kitchenpos.common.domain.vo.exception.InvalidDisplayedNameException;
import kitchenpos.common.domain.vo.Price;
import kitchenpos.common.event.ProductPriceChangedEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product extends AbstractAggregateRoot<Product> {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName displayedName;

    @Embedded
    private Price price;

    protected Product() {
    }

    private Product(final UUID id, final DisplayedName displayedName, final Price price) {
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
    }

    public static Product create(final DisplayedName displayedName, final Long price) {
        if (Objects.isNull(displayedName)) {
            throw new InvalidDisplayedNameException();
        }
        return new Product(UUID.randomUUID(), displayedName, Price.valueOf(price));
    }

    public void changePrice(final Long price) {
        this.price = Price.valueOf(price);
        registerEvent(new ProductPriceChangedEvent(id, price));
    }

    public UUID id() {
        return id;
    }

    public DisplayedName displayedName() {
        return displayedName;
    }

    public Price price() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
