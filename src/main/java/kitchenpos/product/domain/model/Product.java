package kitchenpos.product.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;

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

    public Product(Name name, Price price, UUID id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public Product(Name name, Price price) {
        this(name, price, UUID.randomUUID());
    }

    protected Product() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getInnerName() {
        return name.getValue();
    }

    public Name getName() {
        return name;
    }

//    public void setName(final String name) {
//        this.name = name;
//    }

    public BigDecimal getInnerPrice() {
        return price.getValue();
    }

    public Price getPrice() {
        return price;
    }

    public void changePrice(BigDecimal price) {
        this.price = new Price(price);
    }

//    public void setPrice(final BigDecimal price) {
//        this.price = price;
//    }
}
