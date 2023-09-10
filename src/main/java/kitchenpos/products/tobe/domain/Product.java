package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;

    @Embedded
    @Column(name = "name", nullable = false)
    private DisplayedName name;

    protected Product() {
    }

    private Product(UUID id, Price price, DisplayedName name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public static Product create(UUID id, BigDecimal price, String name, PurgomalumClient purgomalumClient) {
        return new Product(id, Price.of(price), DisplayedName.of(name, purgomalumClient));
    }

    public void changePrice(BigDecimal price) {
        this.price = Price.of(price);
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public String getName() {
        return name.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(price, product.price) && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, name);
    }
}
