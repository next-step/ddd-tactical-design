package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Products {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName displayedName;

    @Embedded
    private Price price;

    public Products() {}

    public Products(PurgomalumClient purgomalumClient, String name, BigDecimal price) {
        this.displayedName = new DisplayedName(purgomalumClient, name);
        this.price = new Price(price);
    }

    public UUID getId() {
        return id;
    }

    public void changePrice(BigDecimal price) {
        this.price = new Price(price);
    }

    public Price getPrice() {
        return price;
    }

}
