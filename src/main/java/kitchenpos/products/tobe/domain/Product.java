package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DisplayedName displayedName;

    @Embedded
    private Price price;

    protected Product() {}

    public Product(DisplayedName displayedName, Price price) {
        this.displayedName = displayedName;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void changePrice(BigDecimal price) {
        this.price = new Price(price);
    }

    public Price getPrice() {
        return price;
    }

}
