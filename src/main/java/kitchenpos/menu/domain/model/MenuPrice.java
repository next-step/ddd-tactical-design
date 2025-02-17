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
            final MenuPriceValidator menuPriceValidator
    ) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new MenuPriceValidationException("메뉴 가격은 0보다 큰 금액이어야 합니다.");
        }
        menuPriceValidator.validate(price);
        return new MenuPrice(price);
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
