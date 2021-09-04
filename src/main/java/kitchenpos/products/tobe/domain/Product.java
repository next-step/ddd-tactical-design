package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    public Product() { }

    public Product(final UUID id, final String name, final BigDecimal price) {
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

    public BigDecimal multiply(final Long quantity) {
        return price.multiply(quantity);
    }

    public void changePrice(final BigDecimal price) {
        this.price = new ProductPrice(price);
    }
}
