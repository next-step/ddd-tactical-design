package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;

public class MenuPrice {

    private BigDecimal price;

    public MenuPrice(BigDecimal price, BigDecimal menuProductsPrice) {
        validatePrice(price, menuProductsPrice);
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
}
