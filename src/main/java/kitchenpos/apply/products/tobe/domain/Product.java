package kitchenpos.apply.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName productName;

    @Embedded
    private ProductPrice productPrice;

    protected Product() { }

    public static Product of(ProductName productName, BigDecimal price) {
        ProductPrice productPrice = new ProductPrice(price);
        return new Product(productName, productPrice);
    }

    public Product(ProductName productName, ProductPrice productPrice) {
        this.id = UUID.randomUUID();
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return productName.value();
    }

    public BigDecimal getPrice() {
        return productPrice.value();
    }

    public void changePrice(BigDecimal price) {
        this.productPrice = new ProductPrice(price);
    }
}
