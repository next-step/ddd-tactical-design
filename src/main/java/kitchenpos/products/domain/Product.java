package kitchenpos.products.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    public Product() {
    }

    public Product(UUID id, String name, BigDecimal price) {
        this(id, new Name(name), new Price(price));
    }

    public Product(UUID id, Name name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = new Name(name);
    }

    public void setPrice(BigDecimal price) {
        this.price = new Price(price);
    }
}
