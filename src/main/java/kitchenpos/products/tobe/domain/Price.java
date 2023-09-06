package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
    private BigDecimal amount;

    public Price() {
    }

    public Price(Long amount) {
        validateIfAmountIsLessThanZero(amount);
        this.amount = BigDecimal.valueOf(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void changeAmount(Long amount) {
        validateIfAmountIsLessThanZero(amount);
        this.amount = BigDecimal.valueOf(amount);
    }

    private void validateIfAmountIsLessThanZero(Long amount) {
        if (Objects.isNull(amount) || amount.compareTo(0L) < 0) {
            throw new IllegalArgumentException();
        }
    }
}
