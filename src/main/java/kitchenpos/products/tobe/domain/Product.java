package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity(name = "ProductTobe")
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

    public Product(final UUID id, final ProductName name, final ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public Product changePrice(final ProductPrice price) {
        this.price = price;
        // Event 발생
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
