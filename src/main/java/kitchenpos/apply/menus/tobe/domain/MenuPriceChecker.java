package kitchenpos.apply.menus.tobe.domain;

import java.math.BigDecimal;

public class MenuPriceChecker {

    public static boolean isTotalPriceLowerThanMenu(BigDecimal menuPrice, BigDecimal totalPrice) {
        return totalPrice.compareTo(menuPrice) < 0;
    }
}
