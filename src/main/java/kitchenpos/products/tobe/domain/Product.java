package kitchenpos.products.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.common.domain.Price;
import kitchenpos.products.infra.PurgomalumClient;

@Table(name = "product")
@Entity
public class Product {

    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private Price price;

    protected Product() { }

    private Product(UUID id, ProductName name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    private Product(String name, Long price, PurgomalumClient purgomalumClient) {
        this(UUID.randomUUID(), new ProductName(name, purgomalumClient), new Price(price));
    }

    public static Product of(String name, Long price, PurgomalumClient purgomalumClient) {
        return new Product(name, price, purgomalumClient);
    }

    public void changePrice(Long price) {
        this.price = new Price(price);
    }

    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
