package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.infra.PurgomalumClient;

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

    private Product(ProductName name, ProductPrice price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
    }

    public static Product of(String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        return new Product(ProductName.from(name, purgomalumClient),
                           ProductPrice.from(price));
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

    public void updatePrice(BigDecimal price) {
        this.price = ProductPrice.from(price);
    }
}
