package kitchenpos.common.values;

import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.exception.KitchenPosExceptionType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;
import static kitchenpos.common.ComparisonUtils.lessThan;

@Embeddable
public class Price {

    @Column(name = "price", nullable = false)
    private final BigDecimal value;


    protected Price() {
        throw new KitchenPosException("DEFAULT 생성자 호출", KitchenPosExceptionType.METHOD_NOT_ALLOWED);
    }

    public Price(final Long value) {
        this(BigDecimal.valueOf(value));
    }
    public Price(final BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private static void validate(BigDecimal value) {
        if (value == null || lessThan(value, ZERO)) {
            String message = String.format("가격이 %s 이므로", value);
            throw new KitchenPosException(message, KitchenPosExceptionType.BAD_REQUEST);
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    public boolean equalValue(BigDecimal value) {
        return this.value.equals(value);
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
        return Objects.hash(value);
    }

    public BigDecimal multiply(BigDecimal bigDecimal) {
        return value.multiply(bigDecimal);
    }
    public BigDecimal multiply(long bigDecimal) {
        return value.multiply(BigDecimal.valueOf(bigDecimal));
    }

}
