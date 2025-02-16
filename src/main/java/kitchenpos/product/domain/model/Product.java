package kitchenpos.product.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    public Product(ProductName name, ProductPrice price, UUID id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public Product(ProductName name, ProductPrice price) {
        this(name, price, UUID.randomUUID());
    }

    protected Product() {

    }

    public Product(String name, BigDecimal price) {
        validateName(name);
        validatePrice(price);
        this.name = name;
        this.price = price;
    }

    private void validateName(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }
    }

    private void validatePrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getInnerName() {
        return name.getValue();
    }

    public ProductName getName() {
        return name;
    }

//    public void setName(final String name) {
//        this.name = name;
//    }

    public BigDecimal getInnerPrice() {
        return price.getValue();
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }

//    public void setPrice(final BigDecimal price) {
//        this.price = price;
//    }
}
