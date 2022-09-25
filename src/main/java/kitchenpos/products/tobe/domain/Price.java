package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.exception.InvalidProductPriceException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

    private static final int MINIMUM_PRICE = 0;

    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected Price() {
    }

    private Price(final BigDecimal value) {
        this.value = value;
    }

    public static Price valueOf(final Long value) {
        if (Objects.isNull(value)) {
            throw new InvalidProductPriceException();
        }
        return valueOf(BigDecimal.valueOf(value));
    }

    public static Price valueOf(final BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < MINIMUM_PRICE) {
            throw new InvalidProductPriceException();
        }
        return new Price(value);
    }

    public BigDecimal value() {
        return value;
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
