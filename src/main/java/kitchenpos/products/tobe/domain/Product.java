package kitchenpos.products.tobe.domain;

import kitchenpos.products.shared.dto.request.ProductCreateRequest;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    public Product(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    protected Product() {
    }

    public static Product of(ProductName productName, ProductPrice productPrice) {
        return new Product(UUID.randomUUID(), productName, productPrice);
    }

    public void changeProductPrice(BigDecimal price) {
        this.price = new ProductPrice(price);
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

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getProductName();
    }

    public BigDecimal getPrice() {
        return price.getProductPrice();
    }
}
