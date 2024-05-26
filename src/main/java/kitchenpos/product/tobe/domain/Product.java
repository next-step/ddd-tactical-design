package kitchenpos.product.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "name", nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private BigDecimal productPrice;

    protected Product() {
        // 외부에서 기본생성자 사용하지 못하도록 접근제어자 변경
    }

    public Product(String productName, BigDecimal productPrice) {
        validateNameAndPrice(productName, productPrice);
        this.id = UUID.randomUUID();
        this.productName = productName;
        this.productPrice = productPrice;
    }

    private void validateNameAndPrice(String productName, BigDecimal productPrice) {
        if (Objects.isNull(productPrice) || productPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalPriceException(this.getClass().getName());
        }
        if (Objects.isNull(productName)) {
            throw new IllegalNameException(this.getClass().getName());
        }
    }

    public UUID getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }
}

