package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Product {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String displayedName;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(UUID id, String displayedName, ProductPrice price) {
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void changePrice(ProductPrice price) {
        this.price = price;
    }
}
