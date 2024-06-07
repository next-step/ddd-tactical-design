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
}
