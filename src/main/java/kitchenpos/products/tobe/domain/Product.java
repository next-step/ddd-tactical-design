package kitchenpos.products.tobe.domain;


import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Product {
    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;
    @Column(name = "name", nullable = false)
    @Embedded
    private ProductName name;
    @Column(name = "price", nullable = false)
    @Embedded
    private ProductPrice price;

    protected Product() {/*no-op*/}

    public Product(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(UUID id, ProductName name, int price) {
        this(id, name, new ProductPrice(price));
    }

    public void changePrice(int price) {
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
