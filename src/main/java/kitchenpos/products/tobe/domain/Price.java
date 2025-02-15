package kitchenpos.products.tobe.domain;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;
@Embeddable
public class Price {
    private final BigDecimal price;

    public Price(BigDecimal price) {
        validate(price);
        this.price = price;
    }

    private void validate(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }
    public BigDecimal getPrice() {
        return price;
    }
}
