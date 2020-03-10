package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private ProductPrice productPrice;

    protected Product() {
    }

    private Product(final String name, final ProductPrice productPrice) {
        this.name = name;
        this.productPrice = productPrice;
    }

    public static Product from(final String name, final BigDecimal price) {
        return new Product(name, ProductPrice.of(price));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }
}
