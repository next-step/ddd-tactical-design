package kitchenpos.products.domain;

import kitchenpos.products.tobe.domain.PurifiedName;
import kitchenpos.products.tobe.domain.VerifiedPrice;

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
    private PurifiedName name;
    @Embedded
    private VerifiedPrice price;

    public Product() {

    }

    public Product(UUID id, PurifiedName name, VerifiedPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public void changePrice(final VerifiedPrice price) {
        this.price = price;
    }

    public BigDecimal multiplyPrice(long quantity){
        return price.multiply(quantity);
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public String getName() {
        return name.getName();
    }
}
