package kitchenpos.products.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import kitchenpos.profanity.infra.ProfanityCheckClient;

@Table(name = "product")
@Entity
public class Product {

    @Id
    @Column(
        name = "id",
        length = 16,
        unique = true,
        nullable = false
    )
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(String name, BigDecimal price, ProfanityCheckClient profanityCheckClient) {
        this(new ProductName(name, profanityCheckClient), new ProductPrice(price));
    }

    public Product(ProductName name, ProductPrice price) {
        this(UUID.randomUUID(), name, price);
    }

    public Product(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void changePrice(ProductPrice newPrice) {
        this.price = newPrice;
    }

    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public ProductPrice getPrice() {
        return price;
    }

    public BigDecimal getPriceValue() {
        return price.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
