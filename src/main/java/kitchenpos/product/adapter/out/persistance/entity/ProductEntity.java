package kitchenpos.product.adapter.out.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductPrice;
import kitchenpos.product.domain.model.ProfanityFilteringProductNameValidator;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class ProductEntity {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public ProductEntity() {
    }

    public ProductEntity(final UUID id, final String name, final BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductEntity of(final Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getPrice());
    }

    public Product toDomain(ProfanityFilteringProductNameValidator profanityFilteringProductNameValidator) {
        ProductName name = ProductName.of(this.name, profanityFilteringProductNameValidator);
        ProductPrice price = ProductPrice.of(this.price);
        return new Product(id, name, price);
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

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }
}
