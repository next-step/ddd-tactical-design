package kitchenpos.product.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.product.domain.exception.ProductPriceValidationException;

import java.math.BigDecimal;

@Embeddable
public class ProductPrice {
    private static final String MESSAGE_PRODUCT_PRICE_MUST_BE_POSITIVE = "상품 가격은 0원 이상 입력해야 합니다.";
    private static final BigDecimal PRICE_ZERO = new BigDecimal(0);
    @Column(name = "price", nullable = false)
    private final BigDecimal price;

    private ProductPrice(final BigDecimal price) {
        this.price = price;
    }

    protected ProductPrice() {
        // 이 메소드를 직접 호출하여 사용하지 말 것
        price = PRICE_ZERO;
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
}
