package kitchenpos.product.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.common.Price;

@Table(name = "product")
@Entity
public class Product {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(UUID id, ProductName name, BigDecimal price) {
        this(id, name, new Price(price));
    }

    public Product(UUID id, ProductName name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void changePrice(BigDecimal price) {
        this.price = new Price(price);
    }

    public Price multiplyPrice(long quantity) {
        return price.multiply(quantity);
    }

    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
