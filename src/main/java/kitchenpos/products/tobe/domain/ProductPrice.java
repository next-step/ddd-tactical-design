package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    private final BigDecimal price;

    protected ProductPrice() {
        price = null;
    }

    public ProductPrice(final BigDecimal price) {
        this.price = price;
    }

    public ProductPrice(final int price) {
        this(BigDecimal.valueOf(price));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
