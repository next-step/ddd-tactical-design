package kitchenpos.products.domain;

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
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(final UUID id, final ProductName name, final ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(final UUID id, final String name, ProductPurgomalumClient purgomalumClient, final BigDecimal price) {
        this.id = id;
        this.name = ProductName.of(name, purgomalumClient);
        this.price = ProductPrice.of(price);
    }

    public Product(final String name, ProductPurgomalumClient purgomalumClient, final BigDecimal price) {
        this.id = UUID.randomUUID();
        this.name = ProductName.of(name, purgomalumClient);
        this.price = ProductPrice.of(price);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public void changePrice(final BigDecimal price) {
        this.price = ProductPrice.of(price);
    }

}
