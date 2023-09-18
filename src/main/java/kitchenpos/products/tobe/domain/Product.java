package kitchenpos.products.tobe.domain;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product extends AbstractAggregateRoot<Product> {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {

    }

    protected Product(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static Product create(ProductName productName, ProductPrice productPrice) {
        return new Product(
                UUID.randomUUID(),
                productName,
                productPrice
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }

    public boolean changePrice(ProductPrice productPrice) {
        if (this.price.equals(productPrice)) {
            return false;
        }

        this.price = productPrice;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
