package kitchenpos.global.vo;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.global.exception.MaximumPriceException;
import kitchenpos.global.exception.MinimumPriceException;
import kitchenpos.global.exception.NullPriceException;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
public class Price implements ValueObject {

    private static final BigDecimal MIN = BigDecimal.ZERO;

    @Column(name = "price", nullable = false)
    @ColumnDefault("0")
    private BigDecimal value;

    protected Price() {
    }

    public Price(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(BigDecimal price) {
        if (Objects.isNull(price)) {
            throw new NullPriceException();
        }

        if (price.compareTo(MIN) < 0) {
            throw new MinimumPriceException();
        }
    }

    public void validateLessThan(Price maximumPrice) {
        if (grateThan(maximumPrice)) {
            throw new MaximumPriceException();
        }
    }

    public boolean grateThan(Price otherPrice) {
        return value.compareTo(otherPrice.value) > 0;
    }

    public Price multiply(Quantity quantity) {
        return new Price(value.multiply(BigDecimal.valueOf(quantity.getValue())));
    }

    public Price add(Price price) {
        return new Price(value.add(price.value));
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
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
