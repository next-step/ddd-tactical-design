package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuPrice {
    private static final BigDecimal MIN = BigDecimal.ZERO;
    private final String INVALID_PRICE_MESSAGE = "잘못된 메뉴 가격 입니다.";

    private final BigDecimal value;

    public MenuPrice(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(BigDecimal price) {
        if (Objects.isNull(price) || MIN.compareTo(price) > 0) {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE);
        }
    }

    public int compareTo(BigDecimal value) {
        return this.value.compareTo(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPrice menuPrice = (MenuPrice) o;
        return Objects.equals(INVALID_PRICE_MESSAGE, menuPrice.INVALID_PRICE_MESSAGE) && Objects.equals(value, menuPrice.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(INVALID_PRICE_MESSAGE, value);
    }
}
