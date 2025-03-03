package kitchenpos.core.products.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.core.shared.domain.AggregateRoot;
import kitchenpos.core.shared.event.ProductPriceChangedEvent;
import kitchenpos.core.shared.identifier.ProductId;

import java.util.Collection;
import java.util.Objects;

@Table(name = "product_tobe")
@Entity(name = "ProductTobe")
public class Product extends AggregateRoot<Product, ProductId> {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false, columnDefinition = "binary(16)"))
    private ProductId id;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false))
    private ProductName name;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "price", nullable = false, columnDefinition = "decimal(19,2)"))
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
        Objects.requireNonNull(newPrice, "변경할 금액은 null이 될 수 없습니다.");
        ProductPriceChangedEvent productPriceChangedEvent = new ProductPriceChangedEvent(this.id, this.price, newPrice);
        this.price = newPrice;

        registerEvent(productPriceChangedEvent);
        return this;
    }

    @Override
    public Collection<Object> domainEvents() {
        return super.domainEvents();
    }
}
