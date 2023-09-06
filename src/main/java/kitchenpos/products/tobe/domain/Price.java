package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    private BigDecimal amount;

    public Price() {
    }

    public Price(BigDecimal amount) {
        validateIfAmountIsLessThanZero(amount);
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void changeAmount(BigDecimal amount) {
        validateIfAmountIsLessThanZero(amount);
        this.amount = amount;
    }

    private void validateIfAmountIsLessThanZero(BigDecimal amount) {
        if (Objects.isNull(amount) || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }
}
