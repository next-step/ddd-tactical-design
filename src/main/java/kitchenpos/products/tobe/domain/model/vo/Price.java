package kitchenpos.products.tobe.domain.model.vo;

import kitchenpos.global.marker.ValueObject;
import kitchenpos.products.tobe.exception.IllegalPriceException;

import java.math.BigDecimal;
import java.util.Objects;

public final class Price implements ValueObject {

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
