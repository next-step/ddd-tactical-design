package kitchenpos.product.domain.model;

import kitchenpos.product.domain.exception.ProductPriceValidationException;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductPrice {
    private static final String MESSAGE_PRODUCT_PRICE_MUST_BE_POSITIVE = "상품 가격은 0원 이상 입력해야 합니다.";

    private final BigDecimal price;

    private ProductPrice(final BigDecimal price) {
        this.price = price;
    }

    public static ProductPrice of(final BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductPriceValidationException(MESSAGE_PRODUCT_PRICE_MUST_BE_POSITIVE);
        }
        return new ProductPrice(price);
    }

    public BigDecimal value() {
        return price;
    }

    public boolean isSamePrice(BigDecimal price) {
        if (price == null) {
            return false;
        }
        return this.price.compareTo(price) == 0;
    }

    public BigDecimal multiply(long quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price);
    }
}
