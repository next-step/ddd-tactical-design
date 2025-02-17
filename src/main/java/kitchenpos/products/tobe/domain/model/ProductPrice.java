package kitchenpos.products.tobe.domain.model;

import static kitchenpos.common.exception.ExceptionDetails.PRICE_LESS_THAN_ZERO_EXCEPTION;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.exception.PriceLessThanZeroException;

@Embeddable
public class ProductPrice {

    @Column(name = "price", nullable = false)
    private final BigDecimal value;

    public ProductPrice(final BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new PriceLessThanZeroException(PRICE_LESS_THAN_ZERO_EXCEPTION.getMessage());
        }
        this.value = price;
    }

    public BigDecimal getValue() {
        return this.value;
    }
}
