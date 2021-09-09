package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity(name = "TobeProduct")
public class Product {
    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Embedded
    private ProductName productName;

    @Embedded
    private ProductPrice productPrice;

    protected Product() {}

    private Product(final UUID id, final ProductName productName, final ProductPrice productPrice) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public Product(final ProductName productName, final ProductPrice productPrice) {
        this(UUID.randomUUID(), productName, productPrice);
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

    public void setPrice(final BigDecimal price) {
        this.productPrice = new ProductPrice(price);
    }
}
