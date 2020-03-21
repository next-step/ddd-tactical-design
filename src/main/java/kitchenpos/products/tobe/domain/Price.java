package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    private BigDecimal value;

    private Price (final BigDecimal value){
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public static Price of(final BigDecimal value) {
        return new Price(value);
    }

}
