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

    public Product(String displayedName, Long price, PurgomalumClient purgomalumClient) {
        this(new DisplayedName(displayedName, purgomalumClient), new Price(price));
    }

    public Product(DisplayedName displayedName, Price price) {
        this.id = UUID.randomUUID();
        this.displayedName = displayedName;
        this.price = price;
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

    public void changePrice(Long amount) {
        price.changeAmount(amount);
    };

}
