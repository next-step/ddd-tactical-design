package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "product")
@Entity(name = "ProductsProduct")
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
        this.displayedName = displayedName;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getDisplayedName() {
        return this.displayedName;
    }

    public Price getPrice() {
        return price;
    }

    public void change(final Price price) {
        this.price = price;
    }
}
