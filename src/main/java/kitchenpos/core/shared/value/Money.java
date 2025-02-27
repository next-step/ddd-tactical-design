package kitchenpos.core.shared.value;

import kitchenpos.core.shared.domain.ValueObject;

import java.math.BigDecimal;

public class Money extends ValueObject<Money> {
    public static final Money ZERO = Money.wons(0);

    private final BigDecimal amount;

    public static Money wons(long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    private Money(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("금액은 null이 될 수 없습니다.");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("금액은 0보다 작을 수 없습니다. 입력된 금액: " + amount);
        }
        this.amount = amount;
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public boolean isLessThan(Money other) {
        return this.amount.compareTo(other.amount) < 0;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[] { amount.doubleValue() };
    }

    @Override
    public String toString() {
        return amount.toString() + "원";
    }

}
