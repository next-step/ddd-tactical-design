package kitchenpos.products.tobe;

import jakarta.persistence.Column;
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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected   Product() {
    }

    public Product(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(UUID uuid, String name, BigDecimal price, ProductValidator validator) {
        validator.delegate(name, price);
        this.id = uuid;
        this.name = name;
        this.price = price;
    }

    public String name() {
        return name;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;

        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
