package kitchenpos.product.domain.model;

import jakarta.persistence.*;

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

    public Product() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name.value();
    }

    public void setName(final String name) {
        this.name = ProductName.of(name);
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public void setPrice(final BigDecimal price) {
        this.price = ProductPrice.of(price);
    }
}
