package kitchenpos.products.tobe.domain.vo;

import java.util.Objects;

public class Price {
    private static final int MINIMUM_PRICE = 0;

    private final long price;

    public Price(final long price) {
        if (price < MINIMUM_PRICE) {
            throw new IllegalArgumentException("상품의 가격은 0 이상이어야 한다.");
        }
        this.price = price;
    }

    public long getPrice() {
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
