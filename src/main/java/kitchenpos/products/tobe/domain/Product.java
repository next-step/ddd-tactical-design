package kitchenpos.products.tobe.domain;

import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.common.tobe.domain.Price;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Product {

    private final UUID id;

    private final DisplayedName displayedName;

    private Price price;

    public Product(final UUID id, final DisplayedName displayedName, final Price price) {
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return displayedName.value();
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public void changePrice(final Price price) {
        this.price = price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }

        final Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
