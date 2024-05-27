package kitchenpos.product.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.exception.IllegalNameException;
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

    @Column(name = "price", nullable = false)
    private BigDecimal productPrice;

    protected Product() {
        // 외부에서 기본생성자 사용하지 못하도록 접근제어자 변경
    }

    public Product(ProductName productName, BigDecimal productPrice) {
        this.id = UUID.randomUUID();
        this.productName = productName;
        this.productPrice = productPrice;
        validateNameAndPrice();
    }

    private void validateNameAndPrice() {
        if (Objects.isNull(productPrice) || productPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalPriceException(productPrice);
        }
    }

    public UUID getId() {
        return id;
    }

    public ProductName getProductName() {
        return productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }
}

