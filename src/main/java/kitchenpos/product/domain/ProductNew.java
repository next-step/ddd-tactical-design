package kitchenpos.product.domain;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "product_new")
@Entity
public class ProductNew {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private ProductName name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private ProductPrice price;

    public static ProductNew newOf(final ProductName name, final ProductPrice price) {
        return new ProductNew(UUID.randomUUID(), name, price);
    }

    public void changePrice(final ProductPrice price) {
        this.price = price;
    }


    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductNew product = (ProductNew) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private ProductNew(final UUID id, final ProductName name, final ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductNew() {
    }
}
