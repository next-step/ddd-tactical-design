package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Embeddable;
import kitchenpos.products.tobe.exception.PriceNegativeException;
import kitchenpos.products.tobe.exception.PriceNullException;

@Embeddable
public class Price {

    private static final int ZERO = 0;

    private BigDecimal value;

    protected Price() {
    }

    public Price(final BigDecimal value) {
        validate(value);
        this.value = value;
    }

    public Price(final long value) {
        this(BigDecimal.valueOf(value));
    }

    private void validate(final BigDecimal value) {
        if (Objects.isNull(value)) {
            throw new PriceNullException();
        }
        if (value.compareTo(BigDecimal.ZERO) < ZERO) {
            throw new PriceNegativeException();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Price price = (Price) o;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
