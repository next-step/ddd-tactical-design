package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.products.infra.PurgomalumClient;
import org.hibernate.internal.build.AllowPrintStacktrace;

@Table(name = "product")
@Entity
public class Product {

    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;
    @Embedded
    @Column(name = "name", nullable = false)
    private ProductName name;

    @Embedded
    @Column(name = "price", nullable = false)
    private ProductPrice price;

    protected Product() {
    }
    public Product(UUID id, String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        this.id = id;
        this.name = new ProductName(name, purgomalumClient);
        this.price = new ProductPrice(price);
    }

    public static Product of(String name, PurgomalumClient purgomalumClient, BigDecimal price) {
        return new Product(UUID.randomUUID(), name, price, purgomalumClient);
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name.getValue();
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }

}
