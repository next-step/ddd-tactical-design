package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.products.infra.PurgomalumClient;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    Product(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = new ProductPrice(price);
    }

    public static Product create(String name, BigDecimal price) {
        if (Objects.isNull(name) || name.length() <= 0) {
            throw new IllegalArgumentException();
        }

        return new Product(UUID.randomUUID(), name, price);
    }

    public void setChangePrice(final BigDecimal price) {
        this.price.setPrice(price);
    }


}