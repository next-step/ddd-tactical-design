package kitchenpos.products.tobe.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.common.tobe.domain.DisplayedName;

@Table(name = "product")
@Entity
public class Product {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private DisplayedName name;

    @Column(name = "price", nullable = false)
    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(final DisplayedName name, final ProductPrice price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public Product changePrice(final ProductPrice price) {
        this.price = price;
        return this;
    }

}
