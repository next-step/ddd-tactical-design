package kitchenpos.menus.domain.tobe;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuPrice {
    private final BigDecimal value;

    public MenuPrice(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        this.value = price;
    }

    public BigDecimal value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuPrice menuPrice = (MenuPrice) o;

        return Objects.equals(value, menuPrice.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
