package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {
    private BigDecimal value;

    protected Price() {
    }

    private Price(final BigDecimal value) {
        if (isInValidPrice(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public static Price of(final BigDecimal price) {
        return new Price(price);
    }

    public BigDecimal getValue() {
        return value;
    }

    private boolean isInValidPrice(final BigDecimal price) {
        return Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0;
    }
}
