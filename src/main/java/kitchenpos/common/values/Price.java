package kitchenpos.common.values;

import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.exception.KitchenPosExceptionType;
import kitchenpos.common.util.ComparisonUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

import static kitchenpos.common.util.ComparisonUtils.lessThan;

@Embeddable
public class Price {

    public static final Price ZERO = new Price(BigDecimal.ZERO);

    @Column(name = "price", nullable = false)
    private BigDecimal value;


    protected Price() {
    }

    public Price(final Long value) {
        this(BigDecimal.valueOf(value));
    }
    public Price(final BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private static void validate(BigDecimal value) {
        if (value == null || lessThan(value, BigDecimal.ZERO)) {
            String message = String.format("가격이 %s 이므로", value);
            throw new KitchenPosException(message, KitchenPosExceptionType.BAD_REQUEST);
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    public boolean equalValue(BigDecimal value) {
        return ComparisonUtils.isEqual(this.value, value);
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

    public Price add(Price price) {
        BigDecimal result = value.add(price.value);
        return new Price(result);
    }

    public Price multiply(long number) {
        return multiply(BigDecimal.valueOf(number));
    }
    public Price multiply(BigDecimal number) {
        BigDecimal result = value.multiply(number);
        return new Price(result);
    }



    public boolean isGreaterThan(Price price) {
        return ComparisonUtils.greaterThan(value, price.value);
    }

}
