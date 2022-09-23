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

@Table(name = "tb_product")
@Entity
public class Product {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Product() {
        this.id = UUID.randomUUID();
    }

    public Product(
            final String name,
            final PurgomalumClient profanity,
            final BigDecimal price
    ) {
        this();
        this.name = new ProductName(name, profanity);
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void changePrice(BigDecimal price) {
        this.price = price;
    }
}
