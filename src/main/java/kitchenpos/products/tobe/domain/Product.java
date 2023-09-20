package kitchenpos.products.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.products.publisher.ProductPriceChangedEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product extends AbstractAggregateRoot<Product> {

    @EmbeddedId
    private ProductId id;

    @Column(name = "name", nullable = false)
    @Embedded
    private ProductDisplayedName name;

    @Column(name = "price", nullable = false)
    @Embedded
    private Price price;

    protected Product() {

    }

    public Product(ProductId id, ProductDisplayedName name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static Product of(String name, BigDecimal price, ProfanityPolicy profanityPolicy) {
        return new Product(
                new ProductId(),
                new ProductDisplayedName(name, profanityPolicy),
                new Price(price)
        );
    }

    public void changePrice(Price price) {
        this.price = price;
    }

    public ProductId getId() {
        return id;
    }

    public UUID getIdValue() {
        return this.id.getValue();
    }

    public String getNameValue() {
        return name.getValue();
    }

    public BigDecimal getPriceValue() {
        return price.getValue();
    }

    public Price getPrice() {
        return price;
    }

    public ProductDisplayedName getName() {
        return name;
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
        return id != null ? id.hashCode() : 0;
    }
}
