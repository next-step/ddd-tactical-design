package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {

    private static final int ZERO = 0;

    private final BigDecimal value;

    private Price(final BigDecimal value) {
        this.value = value;
    }

    public static Price of(final int value) {
        return of(BigDecimal.valueOf(value));
    }

    public static Price of(final Long value) {
        return of(BigDecimal.valueOf(value));
    }

    public static Price of(final BigDecimal value) {
        validate(value);
        return new Price(value);
    }

    private static void validate(final BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < ZERO) {
            throw new IllegalArgumentException();
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
