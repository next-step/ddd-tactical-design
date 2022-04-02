package kitchenpos.products.tobe.domain;

import java.util.Objects;
import java.util.UUID;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Money;

public final class Product {

    private final ProductId id;
    private final DisplayedName name;
    private Money price;

    public Product(ProductId id, DisplayedName name, Money price) {
        validPrice(price);
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(UUID id, String name, long price) {
        this(new ProductId(id), new DisplayedName(name), new Money(price));
    }

    private void validPrice(Money price) {
        if (price.isLessThan(Money.ZERO)) {
            throw new IllegalArgumentException();
        }
    }

    public void changePrice(Money price) {
        validPrice(price);
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name=" + name +
            ", price=" + price +
            '}';
    }
}
