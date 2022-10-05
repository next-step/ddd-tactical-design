package kitchenpos.products.tobe.domain;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity(name = "tobe_product")
public class Product extends AbstractAggregateRoot<Product> {
    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(UUID id, String name, Long price) {
        this.id = id;
        this.name = new DisplayedName(name);
        this.price = new ProductPrice(price);
    }

    public ProductPriceChangedEvent changePrice(Long amount) {
        price.change(amount);
        return registerEvent(new ProductPriceChangedEvent(id, amount));
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
