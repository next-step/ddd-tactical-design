package kitchenpos.products.tobe.domain;

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

    protected Product() {
    }

    public Product(String name, BigDecimal price, ProductPurgomalumClient productPurgomalumClient) {
        this(UUID.randomUUID(), name, price, productPurgomalumClient);
    }

    private Product(UUID id, String name, BigDecimal price, ProductPurgomalumClient productPurgomalumClient) {
        this.id = id;
        this.productName = new ProductName(name, productPurgomalumClient);
        this.productPrice = new ProductPrice(price);
    }

    public static Product of(Product product, ProductPurgomalumClient productPurgomalumClient) {
        return new Product(product.getName(), product.getPrice(), productPurgomalumClient);
    }

    public void changePrice(BigDecimal price) {
        this.productPrice = new ProductPrice(price);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return productName.getName();
    }

    public BigDecimal getPrice() {
        return productPrice.getPrice();
    }
}
