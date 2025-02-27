package kitchenpos.product.domain.model;

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
    private ProductName name;

    @Embedded
    private ProductPrice price;

    public Product(ProductName name, ProductPrice price) {
        this.name = name;
        this.price = price;
        this.id = UUID.randomUUID();
    }

    protected Product() {

    }

    public Product(String name, BigDecimal price) {
        this(new ProductName(name), new ProductPrice(price));
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getInnerName() {
        return name.getValue();
    }

    public ProductName getName() {
        return name;
    }

    public BigDecimal getInnerPrice() {
        return price.getValue();
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }
}
