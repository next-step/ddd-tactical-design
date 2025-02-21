package kitchenpos.products.tobe.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(UUID id, DisplayedName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return this.name;
    }

    public ProductPrice getPrice() {
        return this.price;
    }

    public void changePrice(final BigDecimal newPrice) {
        this.price = new ProductPrice(newPrice);
    }
}

