package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Geonguk Han
 * @since 2020-03-12
 */
public class Price {

    private final BigDecimal price;

    public Price(final BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    private void validatePrice(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal getPrice() {
        return price;
    }
}
