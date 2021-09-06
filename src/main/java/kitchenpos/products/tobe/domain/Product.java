package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.Function;

@Table(name = "product")
@Entity(name = "TobeProduct")
public class Product {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
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

    private Product(final UUID id, final String name, final BigDecimal price) {
        this(id, new ProductName(name), new ProductPrice(price));
    }

    public Product(final String name, final BigDecimal price) {
        this(UUID.randomUUID(), name, price);
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

    public void validateName(final Function<String, Boolean> validator) {
        productName.validate(validator);
    }
}
