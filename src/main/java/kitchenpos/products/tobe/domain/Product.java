package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.domain.DomainEvents;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product extends AbstractAggregateRoot<Product> {

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {

    }

    public Product(String name, int price, PurgomalumClient purgomalumClient) {
        this.id = UUID.randomUUID();
        this.name = new ProductName(name, purgomalumClient);
        this.price = new ProductPrice(price);
    }

    public Product(UUID id, String name, int price){
        this.id = id;
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
    }

    public void changePrice(int price){
        this.price = new ProductPrice(price);
        registerEvent(new ProductPriceChangedEvent(
                this, this.id, this.price));
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
