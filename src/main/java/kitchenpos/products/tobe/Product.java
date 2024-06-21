package kitchenpos.products.tobe;

import jakarta.persistence.*;

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
    @Embedded
    private Money price;


    protected   Product() {
    }

    public Product(UUID id, String name, Money price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(UUID uuid, String name, Money price, ProductValidator validator) {
        validator.delegate(name, BigDecimal.valueOf(100));
        this.id = uuid;
        this.name = name;
        this.price = price;
    }

    public void changePrice(Money price) {
        this.price = price;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Money price() {
        return price;
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
