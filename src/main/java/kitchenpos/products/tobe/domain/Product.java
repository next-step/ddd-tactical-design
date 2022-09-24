package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.vo.ProductName;
import kitchenpos.products.tobe.domain.vo.ProductPrice;

@Table(name = "tb_product")
@Entity
public class Product {

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {
        id = UUID.randomUUID();
    }

    public Product(
            final String name,
            final PurgomalumClient profanity,
            final BigDecimal price
    ) {
        this();
        this.name = new ProductName(name, profanity);
        this.price = new ProductPrice(price);
    }

    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }
}
