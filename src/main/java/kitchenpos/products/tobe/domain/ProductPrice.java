package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductPrice {
    private static final String INVALID_PRICE_MESSAGE = "상품 가격은 0보다 크거다 같아야 합니다.";

    private final BigDecimal price;

    public ProductPrice(final BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE);
        }
        this.price = price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (null == o || getClass() != o.getClass()) return false;
        final ProductPrice that = (ProductPrice) o;
        return price == null ? that.price == null : price.compareTo(that.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
