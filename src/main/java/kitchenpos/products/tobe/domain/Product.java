package kitchenpos.products.tobe.domain;

import javax.persistence.*;
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
    @Column(name = "name", nullable = false)
    private ProductName displayedName;

    @Embedded
    @Column(name = "price", nullable = false)
    private ProductPrice price;

    protected Product() {
    }

    public Product(UUID id, ProductName displayedName, ProductPrice price) {
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
    }

    public Product(ProductName displayedName, ProductPrice price) {
        this(UUID.randomUUID(), displayedName, price);
    }

    public UUID id() {
        return id;
    }

    public ProductName displayedName() {
        return displayedName;
    }

    public String name() {
        return displayedName.name();
    }

    public ProductPrice price() {
        return price;
    }

    public BigDecimal priceValue() {
        return price.price();
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }

    public void changePrice(ProductPrice price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(displayedName, product.displayedName) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, displayedName, price);
    }
}
