package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.ui.ProductRequest;

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
    @Column(name = "name", nullable = false)
    private ProductName productName;

    @Embedded
    @Column(name = "price", nullable = false)
    private ProductPrice productPrice;

    protected Product() { }

    public static Product of(ProductRequest request) {
        ProductName productName = new ProductName(request.getName());
        ProductPrice productPrice = new ProductPrice(request.getPrice());
        return new Product(productName, productPrice);
    }

    public Product(ProductName productName, ProductPrice productPrice) {
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
