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

    protected static Price valueOf(final BigDecimal value) {
        return new Price(value);
    }

    public BigDecimal getValue(){
        return this.value;
    }

}
