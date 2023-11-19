package kitchenpos.common;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Price {

    public static final Price ZERO = new Price(BigDecimal.ZERO);

    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected Price() {
    }

    public Price(BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 없거나 0보다 작은 값으로 변경할 수 없습니다.");
        }

        this.value = value;
    }

    public static Price of(BigDecimal price) {
        return new Price(price);
    }

    public static Price of(Long price) {
        return of(BigDecimal.valueOf(price));
    }

    public Price add(Price other) {
        return new Price(this.value.add(other.value));
    }

    public Price multiply(long quantity) {
        return new Price(this.value.multiply(BigDecimal.valueOf(quantity)));
    }

    public boolean isLowerThan(Price other) {
        return this.value.compareTo(other.value) < 0;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Price)) {
            return false;
        }
        Price price = (Price) o;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
