package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;
    @Embedded
    private DisplayedName displayedName;
    @Embedded
    private Price price;

    public Product() {
    }

    public Product(UUID id, DisplayedName displayedName, Price price) {
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
    }

    public Product(DisplayedName displayedName, Price price) {
        this(null, displayedName, price);
    }

    public void changePrice(BigDecimal price) {
        this.price = new Price(price);
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }

    public String getName() {
        return displayedName.getValue();
    }
}
