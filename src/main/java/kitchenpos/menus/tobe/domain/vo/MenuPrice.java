package kitchenpos.menus.tobe.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuPrice {
    private final BigDecimal value;

    private MenuPrice(BigDecimal value) {
        this.value = value;
    }

    public static MenuPrice of(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        return new MenuPrice(price);
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPrice menuPrice = (MenuPrice) o;
        return Objects.equals(value, menuPrice.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
