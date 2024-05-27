package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    @Column(name = "price", nullable = false)
    private ProductPrice productPrice;

    protected Product() {
    }

    private Product(UUID id, String name, ProductPrice productPrice) {
        validateName(name);
        this.id = id;
        this.name = name;
        this.productPrice = productPrice;
    }

    public static Product create(String name, ProductPrice price) {
        return new Product(UUID.randomUUID(), name, price);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return productPrice.price();
    }

    public void validateName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("상품명은 필수값입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id)
                && Objects.equals(name, product.name)
                && Objects.equals(productPrice.price(), product.productPrice.price());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, productPrice);
    }
}
