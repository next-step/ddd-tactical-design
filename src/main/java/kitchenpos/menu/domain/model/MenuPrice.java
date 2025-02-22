package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuPriceValidationException;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuPrice {
    private final BigDecimal price;

    private MenuPrice(final BigDecimal price) {
        this.price = price;
    }

    public static MenuPrice of(
            final BigDecimal price,
            final BigDecimal menuProductTotalPrice
    ) {
        validateNegativePrice(price);
        validateMenuPriceAgainstTotalProductPrice(price, menuProductTotalPrice);
        return new MenuPrice(price);
    }

    private static void validateNegativePrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new MenuPriceValidationException("메뉴 가격은 0보다 큰 금액이어야 합니다.");
        }
    }

    public static void validateMenuPriceAgainstTotalProductPrice(
            final BigDecimal price,
            final BigDecimal menuProductTotalPrice
    ) {
        if (price.compareTo(menuProductTotalPrice) > 0) {
            throw new MenuPriceValidationException("메뉴 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
        }
    }

    public void validateMenuPriceAgainstTotalProductPrice(BigDecimal menuProductTotalPrice) {
        validateMenuPriceAgainstTotalProductPrice(this.price, menuProductTotalPrice);
    }

    public BigDecimal value() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuPrice menuPrice = (MenuPrice) o;
        return Objects.equals(price, menuPrice.price);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price);
    }
}
