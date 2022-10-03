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
    private ProductName name;
    @Embedded
    private ProductPrice price;

    public Product(final UUID id, final String name, final BigDecimal price) {
        this.id = id;
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
    }

    public Product(final UUID id, final String name, final long price) {
        this(id,name,BigDecimal.valueOf(price));
    }

    public Product() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public void changePrice(final BigDecimal price) {
        this.price = new ProductPrice(price);
    }

    public BigDecimal multiplyPrice(final long quantity){
        return price.multiply(quantity);
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public String getName() {
        return name.getName();
    }
}
