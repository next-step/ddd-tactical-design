package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.products.infra.PurgomalumClient;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {

    }

    public Product(String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        this.id = UUID.randomUUID();
        this.name = new ProductName(name, purgomalumClient);
        this.price = new ProductPrice(price);
    }

    public Product(UUID id, String name, BigDecimal price){
        this.id = id;
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
    }

    public void changePrice(BigDecimal price){
        this.price = new ProductPrice(price);
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
}
