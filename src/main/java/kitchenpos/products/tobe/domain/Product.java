package kitchenpos.products.tobe.domain;


import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import javax.validation.constraints.Email;
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
    private ProductPrice price;

    protected Product() {}

    public Product(final String name, final PurgomalumClient purgomalumClient, final BigDecimal price) {
        this.name = new ProductName(name, purgomalumClient);
        this.price = new ProductPrice(price);
    }

    public String getName() {
        return name.value();
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }
}
