package kitchenpos.common.vo;

import jakarta.persistence.Embeddable;
import kitchenpos.common.exception.NegativePriceException;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {
    private BigDecimal value;

    protected Price() {
    }

    public static Price ZERO() {
        return new Price(0);
    }

    public Price(final long value) {
        this(toBigDecimal(value));
    }

    private static BigDecimal toBigDecimal(long value) {
        return new BigDecimal(value);
    }

    public Price(final BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(BigDecimal value) {
        Objects.requireNonNull(value);

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativePriceException("양수만 입력할 수 있습니다");
        }
    }

    public Price add(Price other) {
        return new Price(value.add(other.value));
    }

    public Price add(BigDecimal other) {
        return new Price(value.add(other));
    }

    public Price add(long other) {
        return new Price(value.add(BigDecimal.valueOf(other)));
    }

    public Price multiply(int quantity) {
        BigDecimal result = value.multiply(BigDecimal.valueOf(quantity));
        return new Price(result);
    }

    public Price multiply(long quantity) {
        BigDecimal result = value.multiply(BigDecimal.valueOf(quantity));
        return new Price(result);
    }

    public Price multiply(PositiveNumber quantity) {
        return multiply(quantity.getValue());
    }

    public boolean isGreaterThan(Price other) {
        return value.compareTo(other.value) > 0;
    }

    public boolean same(Price other){
        return other.equals(this);
    }

    public boolean different(Price other){
        return !same(other);
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
