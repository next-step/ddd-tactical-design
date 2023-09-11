package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Embeddable;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuException;

@Embeddable
public class MenuPrice {

    BigDecimal price;

    protected MenuPrice() {
    }

    public MenuPrice(BigDecimal price) {
        if (isPriceNull(price)) {
            throw new MenuException(MenuErrorCode.PRICE_IS_NULL);
        }
        if (isPriceNegative(price)) {
            throw new MenuException(MenuErrorCode.PRICE_IS_NEGATIVE);
        }
        this.price = price;
    }

    private boolean isPriceNull(BigDecimal price) {
        return price == null;
    }

    private boolean isPriceNegative(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) < 0;
    }

    public BigDecimal getValue() {
        return price;
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
        return Objects.equals(price, menuPrice.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
