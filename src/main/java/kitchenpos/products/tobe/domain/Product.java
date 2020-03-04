package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private ProductPrice productPrice;

    protected Product() {
    }

    private Product(final String name, final BigDecimal price) {
        this.name = name;
        this.productPrice = ProductPrice.valueOf(price);
    }

    public static Product registerProduct(final String name, final BigDecimal price) {
        return new Product(name, price);
    }

    public void forceSetId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return productPrice.checkProductPriceValue();
    }
}
