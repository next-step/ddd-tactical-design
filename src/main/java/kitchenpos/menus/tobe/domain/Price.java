package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

class Price implements Comparable<Price> {
    private static final String EMPTY_NAME_MESSAGE = "가격은 비어있을 수 없습니다.";
    private static final String INVALID_PRICE_MESSAGE = "가격은 0보다 크거다 같아야 합니다.";

    private final BigDecimal price;

     Price(final BigDecimal price) {
        if (null == price) {
            throw new IllegalArgumentException(EMPTY_NAME_MESSAGE);
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE);
        }
        this.price = price;
    }

    public Price multiply(long quantity) {
        return new Price(price.multiply(BigDecimal.valueOf(quantity)));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (null == o || getClass() != o.getClass()) return false;
        final Price menuPrice = (Price) o;
        return price == null ? menuPrice.price == null : price.compareTo(menuPrice.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public int compareTo(final Price o) {
        return price.subtract(o.price).intValue();
    }
}
