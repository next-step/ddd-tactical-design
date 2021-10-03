package kitchenpos.menus.tobe.menu.domain.model;

import static kitchenpos.products.tobe.exception.WrongPriceException.PRICE_SHOULD_NOT_BE_NEGATIVE;
import static kitchenpos.products.tobe.exception.WrongPriceException.PRICE_SHOULD_NOT_BE_NULL;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.common.tobe.domain.Price;
import kitchenpos.products.tobe.exception.WrongPriceException;

@Embeddable
public class MenuPrice implements Price {

    public static final MenuPrice ZERO = new MenuPrice(0);

    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected MenuPrice() {
    }

    public MenuPrice(final long value) {
        this(BigDecimal.valueOf(value));
    }

    public MenuPrice(final BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(final BigDecimal value) {
        if (Objects.isNull(value)) {
            throw new WrongPriceException(PRICE_SHOULD_NOT_BE_NULL);
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new WrongPriceException(PRICE_SHOULD_NOT_BE_NEGATIVE);
        }
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public MenuPrice multiply(final Quantity quantity) {
        return new MenuPrice(value.multiply(BigDecimal.valueOf(quantity.getValue())));
    }

    @Override
    public MenuPrice add(final Price price) {
        return new MenuPrice(value.add(price.getValue()));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MenuPrice price = (MenuPrice) o;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(final Price p) {
        return value.compareTo(p.getValue());
    }

}
