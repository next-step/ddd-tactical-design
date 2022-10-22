package kitchenpos.common.price;

import java.math.BigDecimal;

/**
 * <h1>가격</h1>
 * <ul>
 *   <li>가격은 음수일 수 없다.</li>
 * </ul>
 */
public class Price {

    public final BigDecimal value;

    public Price(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Price는 null일 수 없습니다");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price는 음수일 수 없습니다");
        }
        this.value = value;
    }

    public Price(long value) {
        this(BigDecimal.valueOf(value));
    }

    public Price(double value) {
        this(BigDecimal.valueOf(value));
    }

    public Price add(Price augend) {
        return new Price(this.value.add(augend.value));
    }

    public Price multiply(BigDecimal multiplicand) {
        return new Price(this.value.multiply(multiplicand));
    }

    public Price multiply(long multiplicand) {
        return this.multiply(BigDecimal.valueOf(multiplicand));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Price price = (Price) o;

        return value.equals(price.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
