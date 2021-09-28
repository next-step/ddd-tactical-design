package kitchenpos.common.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Price implements Comparable<Price> {

    public static final Price ZERO = new Price(BigDecimal.ZERO);

    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected Price() {
    }

    public Price(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(final BigDecimal value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("가격은 null이 될 수 없습니다.");
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 0원 이상이어야 합니다.");
        }
    }

    public Price add(Price o) {
        final BigDecimal augend = o.value;
        final BigDecimal addedValue = value.add(augend);
        return new Price(addedValue);
    }

    public Price multiply(Quantity quantity) {
        BigDecimal qty = quantity.toBigDecimal();
        final BigDecimal multipliedValue = value.multiply(qty);
        return new Price(multipliedValue);
    }

    public Price multiply(kitchenpos.eatinorders.tobe.domain.order.Quantity quantity) {
        BigDecimal qty = quantity.toBigDecimal();
        final BigDecimal multipliedValue = value.multiply(qty);
        return new Price(multipliedValue);
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

    @Override
    public int compareTo(final Price o) {
        final BigDecimal val = o.value;
        return value.compareTo(val);
    }
}
