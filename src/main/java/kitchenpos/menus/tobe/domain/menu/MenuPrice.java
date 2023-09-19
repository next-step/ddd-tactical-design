package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Objects;

public class MenuPrice {

    public static final MenuPrice ZERO = MenuPrice.from(BigDecimal.ZERO);
    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected MenuPrice() {
    }

    private MenuPrice(BigDecimal value) {
        this.validate(value);
        this.value = value;
    }

    private void validate(BigDecimal value) {
        if (Objects.isNull(value) || this.isNegativeNumber(value)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isNegativeNumber(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    public static MenuPrice from(BigDecimal value) {
        return new MenuPrice(value);
    }

    public MenuPrice add(MenuPrice menuPrice) {
        return new MenuPrice(this.value.add(menuPrice.value));
    }

    public MenuPrice multiplyByQuantity(long quantity) {
        return new MenuPrice(this.value.multiply(BigDecimal.valueOf(quantity)));
    }

    public boolean isBiggerThan(MenuPrice menuPrice) {
        return this.value.compareTo(menuPrice.value) > 0;
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
        return Objects.hash(value);
    }
}
