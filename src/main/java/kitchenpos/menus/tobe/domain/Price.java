package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.exception.WrongPriceException;

import java.math.BigDecimal;
import java.util.Objects;

public class Price implements Comparable<Price> {
    public static final Price ZERO = new Price(0);

    private BigDecimal value;

    public Price(final BigDecimal value) {
        validate(value);
        this.value = value;
    }

    public Price(final long value) {
        this(BigDecimal.valueOf(value));
    }

    private void validate(final BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new WrongPriceException();
        }
    }
    
    public Price multiply(final long number) {
        return new Price(this.value.multiply(BigDecimal.valueOf(number)));
    }

    public Price multiply(final BigDecimal number) {
        return new Price(this.value.multiply(number));
    }

    public Price add(final Price price) {
        return new Price(this.value.add(price.getValue()));
    }

    public Price subtract(final Price price) {
        return new Price(this.value.subtract(price.getValue()));
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

    @Override
    public int compareTo(final Price o) {
        return value.compareTo(o.getValue());
    }
}
