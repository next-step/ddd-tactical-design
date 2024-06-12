package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName displayedName;

    @Embedded
    private ProductPrice price;

    protected Product () {}

    private Product(UUID id, DisplayedName displayedName, ProductPrice price) {
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
    }

    private static Product of(UUID id, DisplayedName displayedName, ProductPrice price) {
        return new Product(id, displayedName, price);
    }

    public static Product of(DisplayedName displayedName, ProductPrice price) {
        return of(UUID.randomUUID(), displayedName, price);
    }

    public void changePrice(ProductPrice price) {
        this.price = price;
    }

    public UUID getId() {
        return this.id;
    }

    public DisplayedName getDisplayedName() {
        return this.displayedName;
    }

    public ProductPrice getProductPrice() {
        return this.price;
    }
}
