package kitchenpos.products.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
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

    protected Product(final UUID id,
                      final String name,
                      final BigDecimal price,
                      final PurgomalumClient purgomalumClient) {
        this.id = id;
        this.name = new ProductName(name, purgomalumClient);
        this.price = new ProductPrice(price);
    }

    public static Product create(final String name,
                                 final BigDecimal price,
                                 final PurgomalumClient purgomalumClient) {
        return new Product(
                UUID.randomUUID(),
                name,
                price,
                purgomalumClient);
    }

    public void changePrice(final BigDecimal price) {
        this.price = new ProductPrice(price);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return this.name.getName();
    }

    public BigDecimal getPrice() {
        return this.price.getPrice();
    }
}
