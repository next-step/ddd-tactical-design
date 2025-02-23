package kitchenpos.products.tobe.domain.vo;

import kitchenpos.products.tobe.domain.exception.InvalidPricePeriodException;

import java.util.Objects;

public class Price {
    private static final int MINIMUM_PRICE = 0;

    private final long value;

    public Price(final long value) {
        if (value < MINIMUM_PRICE) {
            throw new InvalidPricePeriodException();
        }
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Price that = (Price) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
