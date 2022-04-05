package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    private static final String PRICE_MUST_BE_POSITIVE_NUMBER = "가격은 0이상의 정수 이어야 합니다. 입력 값 : %s";
    private final BigDecimal price;

    protected ProductPrice() {
        this.price = null;
    }

    public ProductPrice(final BigDecimal price) {
        validate(price);
        this.price = price;
    }

    public ProductPrice(final long price) {
        this(BigDecimal.valueOf(price));
    }

    private void validate(BigDecimal price) {
        if (Objects.isNull(price) || isNegative(price)) {
            throw new IllegalArgumentException(PRICE_MUST_BE_POSITIVE_NUMBER);
        }
    }

    private boolean isNegative(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
