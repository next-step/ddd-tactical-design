package kitchenpos.products.tobe.domain;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;

@Table(name = "product")
@Entity
public class Product {

    @Id
    @Column(
        name = "id",
        length = 16,
        unique = true,
        nullable = false
    )
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(String name, ProductPrice price) {
        this(UUID.randomUUID(), name, price);
    }

    public Product(UUID id, String name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
