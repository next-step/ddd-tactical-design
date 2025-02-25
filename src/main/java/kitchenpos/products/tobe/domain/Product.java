package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.shared.domain.DomainEntity;
import kitchenpos.shared.domain.Money;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product_tobe")
@Entity(name = "ProductTobe")
public class Product extends DomainEntity<Product, ProductId> {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private ProductId id;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false))
    private ProductName name;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "price", nullable = false))
    private ProductPrice price;

    @SuppressWarnings("unused")
    protected Product() {}

    private Product(ProductId id, ProductName name, ProductPrice price) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.price = Objects.requireNonNull(price, "price must not be null");
    }

    public static Product create(ProductId id, ProductName name, ProductPrice price) {
        return new Product(id, name, price);
    }

    /**
     * getter
     */
    @Override
    public ProductId getId() {
        return id;
    }

    public ProductName getName() { return name; }

    public ProductPrice getPrice() { return price; }

}
