package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductPrice {
    private static final BigDecimal MIN = BigDecimal.ZERO;
    private static final String INVALID_PRICE_MESSAGE = "상품의 가격은 0원 이상이어야 합니다.";

    private final BigDecimal value;

    public ProductPrice(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(BigDecimal price) {
        if (Objects.isNull(price) || MIN.compareTo(price) > 0) {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE);
        }
    }

    public BigDecimal multiplyPrice(BigDecimal value) {
        return this.value.multiply(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
