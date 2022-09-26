package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.exception.NotNegativePriceException;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

    private BigDecimal price;

    protected Price() {}

    public Price(BigDecimal price) {
        validate(price);
        this.price = price;
    }

    public void change(BigDecimal price) {
        validate(price);
        this.price = price;
    }

    private void validate(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotNegativePriceException();
        }
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price that = (Price) o;
        return price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

}
