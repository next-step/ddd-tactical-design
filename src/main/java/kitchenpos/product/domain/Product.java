package kitchenpos.product.domain;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.common.domain.PurgomalumClient;

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

    public Product(String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        this(UUID.randomUUID(), name, price, purgomalumClient);
    }

    public Product(UUID id, String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        this.id = id;
        this.name = new ProductName(name, purgomalumClient);
        this.price = new ProductPrice(price);
    }

    public void changePrice(long newPrice) {
        this.price = new ProductPrice(newPrice);
    }

    public void changePrice(BigDecimal newPrice) {
        this.price = new ProductPrice(newPrice);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }
}
