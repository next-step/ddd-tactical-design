package kitchenpos.product.domain.model;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;
import kitchenpos.global.exception.ErrorCode;

@Embeddable
public record ProductPrice(BigDecimal price) {

    public static ProductPrice of(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(ErrorCode.PRODUCT_PRICE_NOT_ALLOWED.toString());
        }
        return new ProductPrice(price);
    }
}
