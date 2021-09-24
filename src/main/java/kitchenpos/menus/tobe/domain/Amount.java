package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Amount {
    private static final BigDecimal BOUND_PRICE = BigDecimal.ZERO;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Amount() {
    }

    public Amount(final long price) {
        this(BigDecimal.valueOf(price));
    }

    public Amount(final BigDecimal price) {
        verify(price);
        this.price = price;
    }

    private void verify(final BigDecimal price) {
        if(Objects.isNull(price) || price.compareTo(BOUND_PRICE) < 0) {
            throw new IllegalArgumentException("메뉴 가격의 값이 올바르지 않습니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Amount amount1 = (Amount) o;
        return Objects.equals(price, amount1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
