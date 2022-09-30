package kitchenpos.products.domain;

import javax.persistence.*;
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

    protected Product() {
    }

    public Product(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public void changePrice(final BigDecimal price) {
        this.price.changePrice(price);
    }
}
