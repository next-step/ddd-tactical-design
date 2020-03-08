package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    private BigDecimal price;

    public Price(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException();

        this.price = price;
    }
}
