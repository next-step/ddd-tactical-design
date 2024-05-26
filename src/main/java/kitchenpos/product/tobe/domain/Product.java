package kitchenpos.product.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
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
    }

    public Product(UUID id, String productName, BigDecimal productPrice) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
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

