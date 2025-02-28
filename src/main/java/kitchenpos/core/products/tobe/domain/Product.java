package kitchenpos.core.products.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.core.shared.domain.DomainEntity;
import kitchenpos.core.shared.identifier.ProductId;

import java.util.Objects;

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
        this.id = Objects.requireNonNull(id, "id는 null이 될 수 없습니다.");
        this.name = Objects.requireNonNull(name, "name은 null이 될 수 없습니다.");
        this.price = Objects.requireNonNull(price, "price은 null이 될 수 없습니다.");
    }

    public static Product create(final ProductId id, final ProductName name, final ProductPrice price) {
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

    public Product changePrice(final ProductPrice newPrice) {
        this.price = newPrice;
        return this;
    }
}
