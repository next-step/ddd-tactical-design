package kitchenpos.common.tobe.domain;

import static kitchenpos.products.tobe.exception.WrongPriceException.PRICE_SHOULD_NOT_BE_NEGATIVE;
import static kitchenpos.products.tobe.exception.WrongPriceException.PRICE_SHOULD_NOT_BE_NULL;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.menus.tobe.domain.model.Quantity;
import kitchenpos.products.tobe.exception.WrongPriceException;

@Embeddable
public class Price implements Comparable<Price> {

    public static final Price ZERO = new Price(0);

    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected Price() {
    }

    public Price(final BigDecimal value) {
        validate(value);
        this.value = value;
    }

    public Price(final long value) {
        this(BigDecimal.valueOf(value));
    }

    private void validate(final BigDecimal value) {
        if (Objects.isNull(value)) {
            throw new WrongPriceException(PRICE_SHOULD_NOT_BE_NULL);
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new WrongPriceException(PRICE_SHOULD_NOT_BE_NEGATIVE);
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Price price = (Price) o;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public Price multiply(final Quantity quantity) {
        return new Price(value.multiply(BigDecimal.valueOf(quantity.getValue())));
    }

    public Price add(final Price price) {
        return new Price(value.add(price.getValue()));
    }

    @Override
    public int compareTo(final Price p) {
        return value.compareTo(p.getValue());
    }
}
