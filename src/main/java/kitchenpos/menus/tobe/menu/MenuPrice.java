package kitchenpos.menus.tobe.menu;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuPrice {
    private final BigDecimal value;

    private MenuPrice(BigDecimal value) {
        if (Objects.isNull(value) || value.intValue() < 0) {
            throw new IllegalArgumentException("메뉴의 가격은 0원 이상이어야 합니다.");
        }
        this.value = value;
    }

    public MenuPrice(long value) {
        this(BigDecimal.valueOf(value));
    }

    public boolean isGreaterThan(long other) {
        return value.compareTo(BigDecimal.valueOf(other)) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPrice that = (MenuPrice) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
