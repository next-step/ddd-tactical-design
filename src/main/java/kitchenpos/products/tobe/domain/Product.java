package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

    @Id

    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price = new ProductPrice();

    public Product(String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        this.id = UUID.randomUUID();
        this.name = new ProductName(name, purgomalumClient);
        this.price = new ProductPrice(price);
    }

    protected Product() {
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
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
}