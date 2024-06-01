package kitchenpos.products.tobe.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.domain.vo.ProductName;
import kitchenpos.products.tobe.domain.vo.ProductPrice;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Product() {
    }

    public Product(UUID id, ProductName name, ProductPrice price) {
        this.id = id;
        this.name = name.getValue();
        this.price = price.getValue();
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void changeName(final ProductName name) {
        this.name = name.getValue();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void changePrice(final ProductPrice price) {
        this.price = price.getValue();
    }
}
