package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity(name = "todeProduct")
public class Product {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {}

    private Product(ProductPrice price) {
        this.price = price;
    }

    private Product(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(final BigDecimal price) {
        this(new ProductPrice(price));
    }

    public Product(final UUID id, final String name, final BigDecimal price) {
        this(id, new ProductName(name), new ProductPrice(price));
    }

    public Product(final String name, final long price) {
        this(UUID.randomUUID(), new ProductName(name), new ProductPrice(BigDecimal.valueOf(price)));
    }

    public Product(final String name, final BigDecimal price) {
        this(UUID.randomUUID(), new ProductName(name), new ProductPrice(price));
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

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }
}
