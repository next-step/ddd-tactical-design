package kitchenpos.menus.tobe.menu.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Quantity {
    private static final long BOUND_QUANTITY = 0L;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected Quantity() {
    }

    public Quantity(final long quantity) {
        verify(quantity);
        this.quantity = quantity;
    }

    private void verify(final long quantity) {
        if(quantity < BOUND_QUANTITY) {
            throw new IllegalArgumentException("메뉴 상품의 수량은 0 이상이어야한다.");
        }
    }

    public BigDecimal value() {
        return BigDecimal.valueOf(quantity);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Quantity quantity1 = (Quantity) o;
        return quantity == quantity1.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
