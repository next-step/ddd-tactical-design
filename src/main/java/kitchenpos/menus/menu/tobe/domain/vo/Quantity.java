package kitchenpos.menus.menu.tobe.domain.vo;

import kitchenpos.menus.menu.tobe.domain.vo.exception.InvalidQuantityException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Quantity {

    private static final long MINIMUM_VALUE = 0L;

    @Column(name = "quantity", nullable = false)
    private Long value;

    protected Quantity() {
    }

    private Quantity(final Long value) {
        this.value = value;
    }

    public static Quantity valueOf(final Long value) {
        if (Objects.isNull(value) || value < MINIMUM_VALUE) {
            throw new InvalidQuantityException(value);
        }
        return new Quantity(value);
    }

    public Long value() {
        return value;
    }

    public BigDecimal toBigDecimal() {
        return BigDecimal.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quantity quantity = (Quantity) o;
        return Objects.equals(value, quantity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
