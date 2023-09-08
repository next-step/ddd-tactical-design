package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class ToBeProduct {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "displayed name", nullable = false)
    @Embedded
    private DisplayedName displayedName;

    @Column(name = "price", nullable = false)
    @Embedded
    private Price price;

    public ToBeProduct() {

    }

    public ToBeProduct(String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        this.id = UUID.randomUUID();
        this.displayedName = new DisplayedName(name, purgomalumClient);
        this.price = new Price(price);
    }

    public ToBeProduct(BigDecimal price, PurgomalumClient purgomalumClient) {
        this.id = UUID.randomUUID();
        this.displayedName = new DisplayedName("test", purgomalumClient);
        this.price = new Price(price);
    }

    public void changePrice(BigDecimal price){
        this.price.checkPrice(price);
    }

    public void checkDisplayedName(String name){
        this.displayedName.checkDisplayedName(name);

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return displayedName.getDisplayedName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

}

