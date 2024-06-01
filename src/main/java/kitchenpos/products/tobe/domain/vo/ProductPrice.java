package kitchenpos.products.tobe.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductPrice {
    private final BigDecimal value;

    private ProductPrice(BigDecimal value) {
        this.value = value;
    }

    public static ProductPrice of(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        return new ProductPrice(price);
    }

    public BigDecimal getValue() {
        return value;
    }
}
