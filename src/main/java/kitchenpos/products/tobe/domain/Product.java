package kitchenpos.products.tobe.domain;

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

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private DisplayedName displayedName;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(DisplayedName displayedName, Price price) {
        this.id = UUID.randomUUID();
        this.displayedName = displayedName;
        this.price = price;
    }

    public void changePrice(BigDecimal price) {
        this.price = new Price(price);
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getDisplayedName() {
        return displayedName;
    }

    public Price getPrice() {
        return price;
    }
}