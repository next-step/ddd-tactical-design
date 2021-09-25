package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Amount {
    private static final BigDecimal BOUND_PRICE = BigDecimal.ZERO;

    @Column(name = "price", nullable = false)
    private BigDecimal amount;

    protected Amount() {
    }

    public Amount(final long amount) {
        this(BigDecimal.valueOf(amount));
    }

    public Amount(final BigDecimal amount) {
        verify(amount);
        this.amount = amount;
    }

    private void verify(final BigDecimal amount) {
        if(Objects.isNull(amount) || amount.compareTo(BOUND_PRICE) < 0) {
            throw new IllegalArgumentException("메뉴 가격의 값이 올바르지 않습니다.");
        }
    }

    public void validateUnderPrice(final BigDecimal price) {
        if(amount.compareTo(price) >= 0) {
            throw new IllegalArgumentException("메뉴 가격이 상품 가격보다 크거나 같습니다.");
        }
    }

    public void validateBelowPrice(final BigDecimal price) {
        if(amount.compareTo(price) > 0) {
            throw new IllegalArgumentException("메뉴 가격이 상품 가격보다 큽니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Amount amount1 = (Amount) o;
        return Objects.equals(amount, amount1.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
