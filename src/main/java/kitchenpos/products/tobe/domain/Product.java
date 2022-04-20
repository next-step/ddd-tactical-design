package kitchenpos.products.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(PurgomalumClient purgomalumClient, String name, BigDecimal price) {
        this.id = UUID.randomUUID();
        this.name = new ProductName(purgomalumClient, name);
        this.price = new Price(price);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public void changePrice(BigDecimal price) {
        this.price = new Price(price);
    }
}
