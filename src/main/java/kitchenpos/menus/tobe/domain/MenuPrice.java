package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuPrice {
    private BigDecimal amount;

    public MenuPrice() {
    }

    public MenuPrice(Long amount) {
        validateIfAmountIsLessThanZero(amount);
        this.amount = BigDecimal.valueOf(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int compareTo(BigDecimal val) {
        return amount.compareTo(val);
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
