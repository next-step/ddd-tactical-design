package kitchenpos.products.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Price {
    @Column(name = "price", nullable = false)
    private BigDecimal value;

    public Price(final BigDecimal value) {
        validateBiggerThanZeroPrice(value);
        this.value = value;
    }

    protected Price() {}

    private void validateBiggerThanZeroPrice(final BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price must bigger than zero");
        }
    }

    public BigDecimal multiply(final BigDecimal value) {
        return this.value.multiply(value);
    }

    public BigDecimal value() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Price price = (Price) obj;
        return this.value.equals(price.value);
    }
}
