package kitchenpos.products.tobe.domain.model;

import kitchenpos.products.tobe.domain.events.ProductPriceChangedPublisher;
import kitchenpos.products.tobe.domain.events.ProductPriceChangedEvent;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity(name = "tobe_product")
public class Product {
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

    public void changePrice(Long amount, ProductPriceChangedPublisher publisher) {
        price.change(amount);
        publisher.publish(new ProductPriceChangedEvent(id, amount));
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
