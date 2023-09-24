package kitchenpos.common.domain;

import kitchenpos.common.exception.PriceErrorCode;
import kitchenpos.common.exception.PriceException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price extends ValueObject {
    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected Price() {

    }

    public Price(BigDecimal price) {
        if (price == null) {
            throw new PriceException(PriceErrorCode.PRICE_IS_NULL);
        }
        if (isLessThanZero(price)) {
            throw new PriceException(PriceErrorCode.PRICE_IS_GREATER_THAN_EQUAL_ZERO);
        }
        this.value = price;
    }

    public Price(long price) {
        this(BigDecimal.valueOf(price));
    }

    private boolean isLessThanZero(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) < 0;
    }

    public Price add(Price inputPrice) {
        BigDecimal add = value.add(inputPrice.value);
        return new Price(add);
    }

    public Price multiply(long input) {
        BigDecimal multiply = value.multiply(BigDecimal.valueOf(input));
        return new Price(multiply);
    }

    public boolean isGreaterThan(BigDecimal input) {
        return value.compareTo(input) > 0;
    }

    public boolean equal(BigDecimal price) {
        return value.compareTo(price) == 0;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price that = (Price) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }


}

