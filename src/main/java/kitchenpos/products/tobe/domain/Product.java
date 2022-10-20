package kitchenpos.products.tobe.domain;

import kitchenpos.common.vo.DisplayedName;
import kitchenpos.common.vo.Price;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Price price;

    @Embedded
    private DisplayedName name;

    public Product(final UUID id, final Price price, final DisplayedName name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public Product(final Price price, final DisplayedName name) {
        this.price = price;
        this.name = name;
    }

    public Product(final Price price) {
        this.price = price;
    }

    public Product(BigDecimal price) {
        this(new Price(price));
    }

    protected Product() {
    }

    public UUID getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public DisplayedName getName() {
        return name;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public void setPrice(final Price price) {
        this.price = price;
    }

    public void setName(final DisplayedName name) {
        this.name = name;
    }

    public void changePrice(final Price price) {
        this.price = price;
    }
}
