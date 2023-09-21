package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product")
public class NewProduct {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;

    protected NewProduct() {
    }

    private NewProduct(UUID id, Price price) {
        this.id = id;
        this.price = price;
    }

    public static NewProduct create(UUID id, BigDecimal price) {
        return new NewProduct(id, Price.of(price));
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPriceValue() {
        return price.getPrice();
    }

    public Price getPrice() {
        return price;
    }
}
