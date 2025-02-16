package kitchenpos.common.domain;

import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    private static final String PRICE_CREATION_EXCEPTION = "가격을 채워주세요!";

    @Column(name = "price", nullable = false)
    private final BigDecimal value;

    public Price(BigDecimal value) {
        validatePrice(value);
        this.value = value;
    }

    private void validatePrice(BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(PRICE_CREATION_EXCEPTION);
        }
    }

    public BigDecimal getValue() {
        return value;
    }
}
