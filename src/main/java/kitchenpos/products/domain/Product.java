package kitchenpos.products.domain;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;

    @Embedded
    private ProductPrice price;

    @Embedded
    private ProductName name;

    public Product(ProductPrice price, ProductName name) {
        this.id = UUID.randomUUID();
        this.price = price;
        this.name = name;
    }

    public void changePrice(BigDecimal changePrice) {
        this.price = price.changePrice(changePrice);
    }

    public UUID getId() {
        return id;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public ProductName getName() {
        return name;
    }
}
