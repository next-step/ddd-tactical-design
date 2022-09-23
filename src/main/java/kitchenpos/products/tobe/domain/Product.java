package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.vo.ProductName;
import kitchenpos.products.tobe.domain.vo.ProductNo;
import kitchenpos.products.tobe.domain.vo.ProductPrice;

@Table(name = "tb_product")
@Entity
public class Product {

    @EmbeddedId
    private ProductNo id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {
        this.id = new ProductNo();
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

    public ProductNo getId() {
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
