package kitchenpos.products.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "product")
@Entity
public class Product {

    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() { }

    private Product(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    private Product(UUID id, String name, Long price) {
        this(id, new ProductName(name), new ProductPrice(price));
    }

    public static Product of(UUID id, String name, Long price) {
        return new Product(id, name, price);
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
