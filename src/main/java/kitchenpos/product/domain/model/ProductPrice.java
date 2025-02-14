package kitchenpos.product.domain.model;

import kitchenpos.product.domain.exception.ProductPriceValidationException;

import java.math.BigDecimal;

public class ProductPrice {
    private static final String MESSAGE_PRODUCT_PRICE_MUST_BE_POSITIVE = "상품 가격은 0원 이상이어야 합니다.";
    private final BigDecimal price;

    private ProductPrice(final BigDecimal price) {
        this.price = price;
    }

    public static ProductPrice of(final BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductPriceValidationException(MESSAGE_PRODUCT_PRICE_MUST_BE_POSITIVE);
        }
        return new ProductPrice(price);
    }

    public BigDecimal value() {
        return price;
    }
}
