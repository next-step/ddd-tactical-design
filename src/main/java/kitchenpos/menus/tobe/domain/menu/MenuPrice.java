package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.isNull;

@Embeddable
public final class MenuPrice {
    @Column(name = "price", nullable = false)
    BigDecimal value;

    MenuPrice() {
    }

    private MenuPrice(BigDecimal value) {
        this.value = value;
    }

    public static MenuPrice create(BigDecimal value) {
        validateMenuPriceIsNull(value);
        validateMenuPriceIsNegative(value);

        return new MenuPrice(value);
    }

    public static MenuPrice update(BigDecimal changedValue) {
        validateMenuPriceIsNull(changedValue);
        validateMenuPriceIsNegative(changedValue);

        MenuPrice menuPrice = new MenuPrice();
        menuPrice.value = changedValue;
        return menuPrice;
    }

    private static void validateMenuPriceIsNegative(BigDecimal value) {
        if (isNegative(value)) {
            throw new IllegalArgumentException("메뉴 가격은 음수일 수 없습니다.");
        }
    }

    private static void validateMenuPriceIsNull(BigDecimal value) {
        if (isNull(value)) {
            throw new IllegalArgumentException("메뉴 가격은 비어있을 수 없습니다.");
        }
    }

    private static boolean isNegative(BigDecimal value) {
        return BigDecimal.ZERO.compareTo(value) > 0;
    }

    BigDecimal getValue() {
        return value;
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
