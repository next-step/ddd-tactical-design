package kitchenpos.menus.tobe.menu.domain.product;

import kitchenpos.menus.tobe.menu.domain.Quantity;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {
    private static final BigDecimal BOUND_PRICE = BigDecimal.ZERO;

    private BigDecimal price;

    protected Price() {
    }

    public Price(final long price) {
        this(BigDecimal.valueOf(price));
    }

    public Price(BigDecimal price) {
        verify(price);
        this.price = price;
    }

    private void verify(BigDecimal price) {
        if(Objects.isNull(price) || price.compareTo(BOUND_PRICE) < 0) {
            throw new IllegalArgumentException("가격의 값이 올바르지 않습니다.");
        }
    }

    public BigDecimal calculate(final Quantity quantity) {
        return price.multiply(quantity.value());
    }

    public boolean isBelowAmount(final BigDecimal amount) {
        return this.price.compareTo(amount) <= 0;
    }

    public BigDecimal value() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
