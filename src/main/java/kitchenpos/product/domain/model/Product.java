package kitchenpos.product.domain.model;

import kitchenpos.shared.event.ProductPriceChangedEvent;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Product extends AggregateRoot {
    private final UUID id;
    private final ProductName name;
    private ProductPrice price;

    public Product(final UUID id, final ProductName name, final ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void changePrice(final BigDecimal price) {
        final BigDecimal oldPrice = this.price.value();
        this.price = ProductPrice.of(price);
        registerEvent(new ProductPriceChangedEvent(id, oldPrice, price));
    }

    public boolean isSameName(String name) {
        return this.name.isSameName(name);
    }

    public boolean isSamePrice(BigDecimal price) {
        return this.price.isSamePrice(price);
    }

    public BigDecimal multiplyPrice(long quantity) {
        return this.price.multiply(quantity);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.value();
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
