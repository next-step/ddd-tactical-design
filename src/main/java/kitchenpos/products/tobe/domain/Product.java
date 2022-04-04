package kitchenpos.products.tobe.domain;

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

    @Column(name = "name", nullable = false)
    @Embedded
    private ProductName name;

    @Column(name = "price", nullable = false)
    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(String name, long price, PurgomalumClient purgomalumClient) {
        this.id = UUID.randomUUID();
        this.name = new ProductName(name, purgomalumClient);
        this.price = new ProductPrice(BigDecimal.valueOf(price));
    }

    public void changePrice(long price) {
        this.price = new ProductPrice(BigDecimal.valueOf(price));
    }
}
