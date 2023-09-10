package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        this(UUID.randomUUID(), name, price, purgomalumClient);
    }

    private Product(UUID id, String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        this.id = id;
        this.name = new Name(name, purgomalumClient);
        this.price = new Price(price);
    }

    public static Product of(Product product, PurgomalumClient purgomalumClient) {
        return new Product(product.getName(), product.getPrice(), purgomalumClient);
    }

    public void changePrice(BigDecimal price) {
        this.price = new Price(price);
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
