package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductPrice price;

    @Embedded
    private DisplayedName name;

    public Product(final ProductPrice price, final DisplayedName name) {
        this.price = price;
        this.name = name;
    }

    protected Product() {
    }

    public UUID getId() {
        return id;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public DisplayedName getName() {
        return name;
    }

    public void changePrice(final ProductPrice price) {
        this.price = price;
    }
}
