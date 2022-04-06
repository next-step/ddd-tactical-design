package kitchenpos.common.domain;

import java.math.BigDecimal;
import java.util.Objects;

public final class Money implements Comparable<Money> {

    public static final Money ZERO = new Money(0);

    private final BigDecimal value;

    public Money(BigDecimal value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("Money는 비어 있을 수 없습니다. value: null");
        }
        this.value = value;
    }

    public Money(long value) {
        this(BigDecimal.valueOf(value));
    }

    public boolean isLessThan(Money other) {
        return this.compareTo(other) < 0;
    }

    public boolean isMoreThan(Money other) {
        return this.compareTo(other) > 0;
    }

    public Money plus(Money other) {
        return new Money(this.value.add(other.value));
    }

    public Money times(long i) {
        return new Money(value.multiply(BigDecimal.valueOf(i)));
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
