package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {

    public static final Price ZERO = Price.from(0L);

    private BigDecimal amount;

    public static Price from(long amount) {
        return from(BigDecimal.valueOf(amount));
    }

    public static Price from(BigDecimal amount) {
        return new Price(amount);
    }

    private Price(BigDecimal amount) {
        if (Objects.isNull(amount) || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        this.amount = amount;
    }

    public Price times(long quantity) {
        return Price.from(this.amount.multiply(BigDecimal.valueOf(quantity)));
    }

    public Price plus(Price price) {
        return Price.from(this.amount.add(price.amount));
    }

    public boolean isGreaterThan(Price price) {
        return amount.compareTo(price.amount) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(amount, price.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
