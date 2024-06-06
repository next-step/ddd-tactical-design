package kitchenpos.product.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.exception.IllegalPriceException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProductName productName;

    @Embedded
    private ProductPrice productPrice;

    protected Product() {
        // 외부에서 기본생성자 사용하지 못하도록 접근제어자 변경
    }

    public Product(ProductName productName, ProductPrice productPrice) {
        this(UUID.randomUUID(), productName, productPrice);
    }

    private Product(UUID id, ProductName productName, ProductPrice productPrice) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public UUID getId() {
        return id;
    }

    public String getProductName() {
        return productName.getName();
    }

    public BigDecimal getProductPrice() {
        return productPrice.getPrice();
    }

    public void updateProductPrice(BigDecimal newProductPrice) {
        this.productPrice = new ProductPrice(newProductPrice);
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
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName=" + productName +
                ", productPrice=" + productPrice +
                '}';
    }
}

