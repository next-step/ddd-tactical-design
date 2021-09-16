package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.exception.WrongPriceException;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    private BigDecimal value;

    static final int ZERO = 0;

    public Price(final BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(final BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < ZERO) {
            throw new WrongPriceException();
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        final Price price = (Price) o;
        return getValue().equals(price.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
