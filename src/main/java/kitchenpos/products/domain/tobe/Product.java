package kitchenpos.products.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
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

    public Product(String name, BigDecimal price) {
        this(new ProductName(name), new ProductPrice(price));
    }

    public Product(UUID uuid, String name, BigDecimal price) {
        this(uuid, new ProductName(name), new ProductPrice(price));
    }

    public Product(ProductName name, ProductPrice price) {
        this(UUID.randomUUID(), name, price);
    }

    public Product(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void changePrice(ProductPrice price) {
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public boolean isSamePrice(ProductPrice price) {
        return this.price.equals(price);
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }
}
