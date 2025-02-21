package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    private final BigDecimal price;

    public Price(final BigDecimal price) {
        if (Objects.isNull(price)|| price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("상품의 가격은 0 이상이어야 한다.");
        }
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Price that = (Price) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price);
    }
}
