package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Objects;

class Price {

    public static final Price ZERO = Price.from(BigDecimal.ZERO);
    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected Price() {
    }

    private Price(BigDecimal value) {
        this.validate(value);
        this.value = value;
    }

    private void validate(BigDecimal value) {
        if (Objects.isNull(value) || this.isNegativeNumber(value)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isNegativeNumber(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    public static Price from(BigDecimal value) {
        return new Price(value);
    }

    public Price add(Price price) {
        return new Price(this.value.add(price.value));
    }

    public Price multiplyByQuantity(long quantity) {
        return new Price(this.value.multiply(BigDecimal.valueOf(quantity)));
    }

    public boolean isBiggerThan(Price price) {
        return this.value.compareTo(price.value) > 0;
    }

    public BigDecimal getValue() {
        return value;
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
}
