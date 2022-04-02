package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public final class Money implements Comparable<Money> {

    public static final Money ZERO = new Money(0);

    private final BigDecimal value;

    public Money(BigDecimal value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public Money(long value) {
        this(BigDecimal.valueOf(value));
    }

    public boolean isLessThan(Money other) {
        return this.compareTo(other) < 0;
    }

    @Override
    public int compareTo(Money o) {
        return value.compareTo(o.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money)) {
            return false;
        }
        Money money = (Money) o;
        return Objects.equals(value, money.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Money{" +
            "value=" + value +
            '}';
    }
}
