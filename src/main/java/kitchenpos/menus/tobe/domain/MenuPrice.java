package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuPrice {

    private BigDecimal price;

    public MenuPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    private void validatePrice(BigDecimal price, BigDecimal menuProductsPrice) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        if (price.compareTo(menuProductsPrice) > 0) {
            throw new IllegalArgumentException();
        }
    }

    public boolean greaterThan(BigDecimal otherPrice) {
        return price.compareTo(otherPrice) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuPrice price1 = (MenuPrice) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
