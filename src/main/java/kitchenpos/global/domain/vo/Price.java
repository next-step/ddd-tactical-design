package kitchenpos.global.domain.vo;

import kitchenpos.global.marker.ValueObject;
import kitchenpos.global.exception.IllegalPriceException;

import java.math.BigDecimal;
import java.util.Objects;

public final class Price implements ValueObject {

    public static final Price ZERO = new Price(BigDecimal.ZERO);

    private final BigDecimal price;

    public Price(BigDecimal price) {
        validate(price);
        this.price = price;
    }

    private void validate(BigDecimal price) {
        if (Objects.isNull(price) || BigDecimal.ZERO.compareTo(price) > 0) {
            throw new IllegalPriceException();
        }
    }

    public boolean isSame(BigDecimal price) {
        return this.price.compareTo(price) == 0;
    }

    public boolean isNotSame(BigDecimal price) {
        return !isSame(price);
    }

    public Price add(Price price) {
        if(price.isSame(BigDecimal.ZERO)) {
            return this;
        }
        return new Price(this.price.add(price.price));
    }

    public Price multiply(long quantity) {
        return new Price(this.price.multiply(BigDecimal.valueOf(quantity)));
    }


    public boolean lessThanEquals(Price other) {
        return this.price.compareTo(other.price) <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }


}
