package kitchenpos.products.tobe.domain.model;

import static kitchenpos.products.tobe.exception.WrongPriceException.PRICE_SHOULD_NOT_BE_NEGATIVE;
import static kitchenpos.products.tobe.exception.WrongPriceException.PRICE_SHOULD_NOT_BE_NULL;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Embeddable;
import kitchenpos.products.tobe.exception.WrongPriceException;

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
            throw new WrongPriceException(PRICE_SHOULD_NOT_BE_NULL);
        }
        if (value.compareTo(BigDecimal.ZERO) < ZERO) {
            throw new WrongPriceException(PRICE_SHOULD_NOT_BE_NEGATIVE);
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
