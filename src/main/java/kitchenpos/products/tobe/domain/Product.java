package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private DisplayedName displayedName;

    @Column(name = "price", nullable = false)
    @Embedded
    private Price price;

    public Product() {
    }

    public Product(String displayedName, BigDecimal price, PurgomalumClient purgomalumClient) {
        this.id = UUID.randomUUID();
        this.displayedName = new DisplayedName(displayedName, purgomalumClient);
        this.price = new Price(price);
    }

    public UUID getId() {
        return id;
    }

    public String getDisplayedName() {
        return displayedName.getName();
    }

    public BigDecimal getPrice() {
        return price.getAmount();
    }

    public void changePrice(BigDecimal amount) {
        price.changeAmount(amount);
    };

}
