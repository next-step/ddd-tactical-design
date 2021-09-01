package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity(name = "ProductsProduct")
public class Product {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Product() {
    }

    public Product(final String name, final Long price) {
        this.name = new DisplayedName(name);
        this.price = BigDecimal.valueOf(price);
    }

    public Product(final DisplayedName name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
